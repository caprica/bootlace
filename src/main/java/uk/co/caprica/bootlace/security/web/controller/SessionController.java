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

package uk.co.caprica.bootlace.security.web.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Web-service controller providing the currently authenticated user.
 * <p>
 * There is no explicit "login" resource, rather a login will be triggered simply by attempting to
 * access this "user" resource (because the Principal is injected into the method).
 * <p>
 * Specifically, the web-service client will attempt to access the "user" resource either with or
 * without "Basic" credentials. If valid credentials were provided (and the user is not already
 * authenticated) a new Spring Security session will be created, effectively logging this user in.
 * <p>
 * The authenticated user principal information is then returned to the web-service client.
 * <p>
 * If no or invalid credentials were provided an authorisation failure will occur and a standard
 * HTTP 401 "Unauthorized" error response will be returned.
 * <p>
 * {@link RestController} adds {@link Controller} and {@link ResponseBody} by default.
 */
@RestController
public class SessionController {

    /**
     * Get the currently authenticated user.
     *
     * @param principal currently authenticated principal
     * @return currently authenticated principal
     */
    @RequestMapping("user")
    public Principal user(Principal principal) {
        return principal;
    }

}
