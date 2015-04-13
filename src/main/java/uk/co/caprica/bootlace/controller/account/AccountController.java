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

package uk.co.caprica.bootlace.controller.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.caprica.bootlace.controller.ResourceNotFoundException;
import uk.co.caprica.bootlace.data.mongo.account.AccountRepository;
import uk.co.caprica.bootlace.domain.account.Account;

/**
 * Web-service controller for Account resources.
 * <p>
 * {@link RestController} adds {@link Controller} and {@link ResponseBody} by default.
 */
@RestController
@RequestMapping("accounts")
public class AccountController {

    /**
     * Log.
     */
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**
     * Repository containing Account resources.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     *
     *
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public List<Account> getAllAccounts() {
        logger.debug("getAllAccounts()");
        List<Account> result = accountRepository.findAll();
        logger.debug("result={}", result);
        return result;
    }

    /**
     *
     *
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.GET, value="{id}")
    @Secured("ROLE_ADMIN")
    public Account getAccount(@PathVariable("id") String id) {
        logger.debug("getAccount(id={})", id);
        Account result = accountRepository.findOne(id);
        logger.debug("result={}", result);
        if (result != null) {
            return result;
        }
        else {
            throw new ResourceNotFoundException();
        }
    }

}
