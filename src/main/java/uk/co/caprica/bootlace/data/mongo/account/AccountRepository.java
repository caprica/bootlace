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

package uk.co.caprica.bootlace.data.mongo.account;

import org.springframework.data.mongodb.repository.MongoRepository;

import uk.co.caprica.bootlace.domain.account.Account;

/**
 * Standard repository containing Account resources.
 * <p>
 * Simply declaring this interface generates a repository with standard CRUD methods.
 * <p>
 * In this case an extra method is declared to find a resource by a particular field - the
 * implementation for this method is generated automatically based on the method signature.
 */
public interface AccountRepository extends MongoRepository<Account, String> {

    /**
     * Find an account for a given username.
     *
     * @param username username to search for
     * @return account; or <code>null</code> if no account found for the give username
     */
    Account findByUsername(String username);

}
