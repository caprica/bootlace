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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Implementation of an auditor component that uses the username of the currently authenticated
 * user.
 */
public class UsernameAuditor implements AuditorAware<String> {

    /**
     * Log.
     */
    private final Logger logger = LoggerFactory.getLogger(UsernameAuditor.class);

    @Override
    public String getCurrentAuditor() {
        logger.debug("getCurrentAuditor()");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String result;
        if (authentication != null && authentication.isAuthenticated()) {
            result = authentication.getName();
        }
        else {
            result = null;
        }
        logger.debug("result={}", result);
        return result;
    }

}
