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

package uk.co.caprica.bootlace.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import uk.co.caprica.bootlace.domain.AbstractAuditableEntity;

/**
 * Configuration for the data repository for this application.
 * <p>
 * This component uses {@link EnableMongoAuditing} to enable the use of annotations to configure
 * auditing (e.g. created/modified by and date, and version number) of entities stored in the
 * repository.
 *
 * @see AbstractAuditableEntity
 */
@Configuration
@EnableMongoAuditing
public class RepositoryConfiguration {

    /**
     * Provide an implementation of an auditor component.
     * <p>
     * This component is used to set the created/modified by user identifier when storing entities.
     *
     * @return auditor component
     */
    @Bean
    public UsernameAuditor auditor() {
        return new UsernameAuditor();
    }

}
