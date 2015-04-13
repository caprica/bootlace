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

package uk.co.caprica.bootlace.startup;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.TextIndexDefinition.TextIndexDefinitionBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import uk.co.caprica.bootlace.data.mongo.account.AccountRepository;
import uk.co.caprica.bootlace.domain.account.Account;

/**
 * Component containing operations to execute on application startup.
 * <p>
 * This component initialises the configured database with seed data.
 * <p>
 * <em>This component only exists for purposes of this reference application and would not
 * ordinarily be used in a Production application.</em>
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Log.
     */
    private final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    /**
     * Lower-level interface to the database, used e.g. to create collections and indexes.
     */
    @Autowired
    private MongoOperations mongoOperations;

    /**
     * Repository for account entities.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Password encoder component.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("onApplicationEvent(event={})", event);
        createDatabase();
        seedDatabase();
    }

    /**
     * Create the various database collections and indexes for this application.
     */
    private void createDatabase() {
        logger.debug("createDatabase()");
        try {
            mongoOperations.createCollection("account");
            mongoOperations.indexOps("account")
                .ensureIndex(new TextIndexDefinitionBuilder()
                    .named("username")
                    .onField("username")
                    .build());
        }
        catch (Exception e) {
            logger.debug("Exception creating database, assuming database already exists");
            // This is most likely because the collection already exists, so ignore the error and
            // carry on
        }
    }

    /**
     * Populate the database with seed data, in particular some demo accounts are created so that
     * application logins and role-based authorisations will work.
     */
    private void seedDatabase() {
        logger.debug("seedDatabase()");
        List<String> adminRoles = new ArrayList<>();
        adminRoles.add("ROLE_ADMIN");
        adminRoles.add("ROLE_USER");
        List<String> userRoles = new ArrayList<>();
        userRoles.add("ROLE_USER");
        if (accountRepository.findByUsername("mark") == null) {
            Account markAdminAccount = new Account();
            markAdminAccount.setUsername("mark");
            markAdminAccount.setPassword(passwordEncoder.encode("bimble"));
            markAdminAccount.setRoles(adminRoles);
            accountRepository.save(markAdminAccount);
        }
        if (accountRepository.findByUsername("admin") == null) {
            Account demoAdminAccount = new Account();
            demoAdminAccount.setUsername("admin");
            demoAdminAccount.setPassword(passwordEncoder.encode("admin"));
            demoAdminAccount.setRoles(adminRoles);
            accountRepository.save(demoAdminAccount);
        }
        if (accountRepository.findByUsername("user") == null) {
            Account demoUserAccount = new Account();
            demoUserAccount.setUsername("user");
            demoUserAccount.setPassword(passwordEncoder.encode("user"));
            demoUserAccount.setRoles(userRoles);
            accountRepository.save(demoUserAccount);
        }
    }

}
