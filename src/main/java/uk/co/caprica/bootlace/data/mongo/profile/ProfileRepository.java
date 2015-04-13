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

package uk.co.caprica.bootlace.data.mongo.profile;

import org.springframework.data.mongodb.repository.MongoRepository;

import uk.co.caprica.bootlace.domain.profile.Profile;

/**
 * Standard repository containing Profile resources.
 * <p>
 * Simply declaring this interface generates a repository with standard CRUD methods.
 */
public interface ProfileRepository extends MongoRepository<Profile, String> {

}
