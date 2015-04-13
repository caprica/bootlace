'use strict';

/**
 * Controller for the Account view.
 * 
 * @param account account resource, provided by the resolver
 */
AccountController.$inject = ['account'];
function AccountController(account) {

    // Use the view-model pattern
    var vm = this;

    // Make the data available to the view template
    vm.account = account;

};

/**
 * Implementation of a resolver that provides a user account from the web-service. 
 */
AccountController.resolve = {
    account: ['$route', 'Restangular', function($route, Restangular) {
        return Restangular.one('accounts', $route.current.params.account).get();
    }]
};

module.exports = AccountController;
