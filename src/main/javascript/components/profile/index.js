'use strict';

/**
 * Module definition for the Profile component.
 *
 * The security module is specified as a dependency so the route can see the 'ROLES' constant.
 */
module.exports = angular.module('bootlace.profile', ['bootlace.security'])
    .config    (                     require('./ProfileRoutes'    ))
    .controller('ProfileController', require('./ProfileController'))
;
