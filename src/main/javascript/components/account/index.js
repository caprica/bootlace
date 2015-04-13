'use strict';

/**
 * Module definition for the Account component.
 * 
 * The security module is specified as a dependency so the route can see the 'ROLES' constant.
 */
module.exports = angular.module('bootlace.account', ['bootlace.security'])
    .config    (                      require('./AccountRoutes'     ))
    .controller('AccountsController', require('./AccountsController'))
    .controller('AccountController' , require('./AccountController' ))
;
