/*
 * This file is part of Bootlace.
 *
 * Copyright (C) 2015
 * Caprica Software Limited (capricasoftware.co.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.caprica.bootlace.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;

import uk.co.caprica.bootlace.data.mongo.account.AccountRepository;
import uk.co.caprica.bootlace.domain.account.Account;
import uk.co.caprica.bootlace.security.domain.UserWithId;
import uk.co.caprica.bootlace.security.web.filter.AngularJsCsrfHeaderFilter;

/**
 * Security configuration.
 * <p>
 * The {@link EnableWebMvcSecurity} annotation enables most things, but
 * {@link EnableGlobalMethodSecurity} is also needed to enable method level security using the
 * {@link Secured} and {@link PreAuthorize} annotations.
 * <p>
 * Spring Security expects that all role names have a recognisable common prefix, by default this
 * is "ROLE_" - typically leading to actual role names like "ROLE_ADMIN" - the simplest approach
 * therefore is to use role names exactly this in the account repository.
 * <p>
 * Implementation notes...
 * </p>
 * The bespoke CSRF filter <em>must</em> be added to the security filter configuration
 * <em>after</em> the {@link SessionManagementFilter}, not after the {@link CsrfFilter}. If it is
 * added after the CsrfFilter then a POST request after the initial login will <em>fail</em>,
 * unless there is an intervening GET request. This is related to getting a new token value after a
 * successful login.
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Name of the header that contains the CSRF token for AngularJS.
     */
    private static final String ANGULARJS_CSRF_TOKEN_HEADER = "X-XSRF-TOKEN";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Unsecured access must be allowed to the application root, the default welcome page, all
        // of the URLs for the application routes, and all of the assets (Javascript/CSS etc) and
        // components (HTML partials for AngularJS templates)
        http
            .httpBasic()
                .and()
            .authorizeRequests()
                .antMatchers("/", "/index.html", "/app/**", "/assets/**", "/components/**")
                .permitAll()
            .anyRequest()
                .authenticated()
                .and()
            .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and()
            .addFilterAfter(new AngularJsCsrfHeaderFilter(), SessionManagementFilter.class)
            .logout()
                .logoutSuccessHandler(logoutSuccessHandler());
    }

    /**
     * Configure the authentication manager to use the custom user details service and password
     * encoder.
     *
     * @param auth authentication manager builder
     * @throws Exception if an error occurs
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(getUserDetailsService())
            .passwordEncoder(passwordEncoder);
    }

    /**
     * Create a CSRF token repository.
     * <p>
     * Use the standard CSRF token repository, but change the header name to that used by
     * AngularJS.
     *
     * @return CSRF token repository
     */
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(ANGULARJS_CSRF_TOKEN_HEADER);
        return repository;
    }

    /**
     * Create a logout success handler.
     * <p>
     * Simply return an appropriate response code instead of using the default handler that wants
     * to force a page redirect to "login?logout".
     *
     * @return logout success handler
     */
    private LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.sendError(HttpServletResponse.SC_NO_CONTENT);
            }
        };
    }

    /**
     * Create a user details service implementation.
     * <p>
     * This method creates a simple user details service implementation that wraps the account
     * repository to convert an Account entity into a Spring Security {@link UserDetails} instance.
     * <p>
     * This service provides username, password and roles. It does not, although it could, use any
     * of the other account-related fields (e.g. for locked or expired accounts).
     *
     * @return user details service implementation
     */
    private UserDetailsService getUserDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Account account = accountRepository.findByUsername(username);
                if (account != null) {
                    return new UserWithId(
                        account.getUsername(),
                        account.getPassword(),
                        true, // enabled
                        true, // account non-expired
                        true, // credentials non-expired
                        true, // account non-locked
                        AuthorityUtils.createAuthorityList(roles(account)),
                        account.getId());
                }
                else {
                    throw new UsernameNotFoundException("User not found '" + username + "'");
                }
            }
        };
    }

    /**
     * Map account roles to roles authorities that will be recognised by Spring Security as
     * authorities.
     *
     * @param account account containing roles
     * @return list of role names, like "ROLE_ADMIN"
     */
    private String[] roles(Account account) {
        String[] result;
        List<String> roles = account.getRoles();
        if (roles != null) {
            result = new String[roles.size()];
            result = roles.toArray(result);
        }
        else {
            result = new String[0];
        }
        return result;
    }

}
