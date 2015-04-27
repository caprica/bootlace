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

package uk.co.caprica.bootlace.domain.account;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import uk.co.caprica.bootlace.domain.AbstractEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Model for an Account resource.
 */
@Document
public class Account extends AbstractEntity {

    /**
     * Username.
     */
    @NotNull
    private String username;

    /**
     * Encrypted password.
     *
     * Using {@link JsonIgnore} prevents the password being returned by a web-service.
     */
    @JsonIgnore
    @NotNull
    private String password;

    /**
     * List of role authorisations.
     */
    private List<String> roles;

    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return toStringHelper()
            .add("username", username)
            .add("roles", roles)
            .toString();
    }

}
