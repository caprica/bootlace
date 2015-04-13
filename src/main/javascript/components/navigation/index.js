'use strict';

/**
 * Module definition for the Navigation component.
 */
module.exports = angular.module('bootlace.navigation', [])
    .controller('NavigationController', require('./NavigationController'))
    .service   ('NavigationService'   , require('./NavigationService'   ))
;