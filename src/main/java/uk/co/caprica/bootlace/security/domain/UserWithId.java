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

package uk.co.caprica.bootlace.security.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Extension of the standard Spring Security {@link User} to hold the database user identifier.
 * <p>
 * Instances of this class are <em>not</em> expected to be serialised to the client via a
 * web-service, rather this class is intended to be used on the server-side only to implement
 * application logic depending on the currently authenticated user (e.g. in web-service
 * controllers).
 */
public final class UserWithId extends User {

    /**
     * The database user identifier.
     */
    private final String id;

    /**
     * Create a user.
     *
     * @param id database user identifier
     */
    public UserWithId(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    /**
     * Get the database identifier for the user.
     *
     * @return unique user identifier
     */
    public String getId() {
        return id;
    }

}
