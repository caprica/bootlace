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

package uk.co.caprica.bootlace.security.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * Implementation of a Spring HTTP request filter that returns an anti cross-site request forgery
 * cookie that is compatible with the AngularJS HTTP provider implementation.
 * <p>
 * This component is required because AngularJS uses a name for the cookie that does not match the
 * standard name.
 */
public final class AngularJsCsrfHeaderFilter extends OncePerRequestFilter {

    /**
     * Name of the AngularJS CSRF cookie.
     */
    private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

    /**
     * Log.
     */
    private final Logger logger = LoggerFactory.getLogger(AngularJsCsrfHeaderFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("doFilterInternal()");
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrf != null) {
            Cookie cookie = WebUtils.getCookie(request, CSRF_COOKIE_NAME);
            String token = csrf.getToken();
            if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                logger.debug("Setting new CSRF cookie");
                cookie = new Cookie(CSRF_COOKIE_NAME, token);
                cookie.setPath(request.getServletContext().getContextPath() + "/");
                response.addCookie(cookie);
            }
        }
        filterChain.doFilter(request, response);
    }

}
