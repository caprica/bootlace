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

(function () {

    'use strict';

    // Unfortunately this seems to required for e.g. toastr
    window.jQuery = require('jquery');

    require('bootstrap');

    var angular = require('angular');

    // Application module definition
    angular.module('bootlace', [

        // Module dependencies
        require('angular-animate'        ),
        require('angular-aria'           ),
        require('angular-route'          ),
        require('angular-cache'          ),

        // Shimmed module dependencies - the names here are those used in the "browserify-shim"
        // section of the project "package.json"
        require('angular-google-maps'    ).name,
        require('angular-toastr'         ).name,
        require('restangular'            ).name,
    
        // Application components - this will pull in "index.js" from each directory
        require('./components/about'     ).name,
        require('./components/account'   ).name,
        require('./components/common'    ).name,
        require('./components/error'     ).name,
        require('./components/home'      ).name,
        require('./components/map'       ).name,
        require('./components/navigation').name,
        require('./components/profile'   ).name,
        require('./components/security'  ).name

    ]);

}());
