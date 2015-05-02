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

package uk.co.caprica.bootlace.domain.profile;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import uk.co.caprica.bootlace.domain.AbstractAuditableEntity;

/**
 * Model for a Profile resource.
 */
@Document
public class Profile extends AbstractAuditableEntity {

    @NotNull
    private String forename;

    @NotNull
    private String surname;

    @NotNull
    private String email;

    public Profile() {
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return toStringHelper()
            .add("forename", forename)
            .add("surname", surname)
            .add("email", email)
            .toString();
    }

}
