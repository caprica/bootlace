'use strict';

/**
 * Controller for the Accounts view.
 * 
 * @param accounts list of account resources, provided by the resolver
 */
AccountsController.$inject = ['accounts'];
function AccountsController(accounts) {

    // Use the view-model pattern
    var vm = this;

    // Make the data available to the view template
    vm.accounts = accounts;

};

/**
 * Implementation of a resolver that provides the list of user accounts from the web-service. 
 */
AccountsController.resolve = {

    accounts: ['Restangular', function(Restangular) {
        return Restangular.all('accounts').getList();
    }]

};

module.exports = AccountsController;
