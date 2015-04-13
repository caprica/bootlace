'use strict';

/**
 * Controller for the Navigation view.
 * 
 * @param NavigationService navigation service, to manage navigation state
 */
NavigationController.$inject = ['NavigationService'];
function NavigationController(NavigationService) {

    // Use the view-model pattern
    var vm = this;

    // Make the data available to the view template
    vm.active = NavigationService.getActive;
    
};

module.exports = NavigationController;
