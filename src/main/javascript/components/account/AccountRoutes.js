'use strict';

/**
 * Router configuration for the Account component.
 */
AccountRoutes.$inject = ['$locationProvider', '$routeProvider', 'ROLES'];
function AccountRoutes($locationProvider, $routeProvider, roles) {

    $routeProvider
        .when('/accounts', {
            controller   : 'AccountsController',
            controllerAs : 'accounts',
            templateUrl  : '/components/account/accounts.html',
            title        : 'Accounts',
            active       : 'accounts',
            resolve      : require('./AccountsController').resolve,
            authorisation: {
                requires: roles.admin
            }
        })
        .when('/accounts/:account', {
            controller   : 'AccountController',
            controllerAs : 'account',
            templateUrl  : '/components/account/account.html',
            title        : 'Account',
            active       : 'accounts',
            resolve      : require('./AccountController').resolve,
            authorisation: {
                requires: roles.admin
            }
        });

}

module.exports = AccountRoutes;
