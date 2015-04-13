'use strict';

/**
 * Module definition for the Home component.
 */
module.exports = angular.module('bootlace.home', [])
    .config    (                   require('./HomeRoutes'     ))
    .controller('HomeController' , require('./HomeController' ))
;
