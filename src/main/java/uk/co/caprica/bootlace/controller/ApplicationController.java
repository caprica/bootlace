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

package uk.co.caprica.bootlace.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// FIXME there may be a better way to bind these particular request mappings, i'm not sure
// FIXME maybe "app" should be the app name for nicer urls?
// FIXME ideally the app.html would be served from WEB-INF so it can't be accessed directly (i.e. can't be accessed without a valid route)

/**
 * Main application controller.
 * <p>
 * The sole responsibility of this controller is to route <em>all</em> requests to the single HTML
 * page for the application. This includes the default application URL, the default "index.html"
 * URL and any deep-link URL to a specific page within the application.
 * <p>
 * All routing to individual pages within the application is done by the client-side router.
 * <p>
 * Similar, perhaps simpler, configuration could probably be achieved by tweaking ".htaccess" if an
 * Apache web server is used.
 */
@Controller
public class ApplicationController {

    /**
     * Log.
     */
    private final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    /**
     * Default handler simply redirects to the single application HTML page.
     *
     * @return redirect
     */
    @RequestMapping({"/", "/index.html"})
    public String index() {
        logger.debug("index()");
        return "redirect:/app";
    }

    /**
     * Map all application links (including deep links) to the single application HTML page.
     * <p>
     * When the application HTML page renders, a client-side router will take over.
     */
    @RequestMapping("app/**")
    public String app() {
        logger.debug("app()");
        return "forward:/assets/index.html";
    }

}
