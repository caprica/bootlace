'use strict';

/**
 * Service that manages navigation state.
 */
NavigationService.$inject = ['$log'];
function NavigationService($log) {

    var tag = '   NavigationService:';

    /**
     * Service exports.
     */
    this.setActive = SetActive;
    this.getActive = GetActive;  

    /**
     * The currently active view-state, with a reasonable default.
     *
     * This value can be used e.g. to highlight an item in a navigation bar corresponding to the
     * active view-state.
     */
    var active = 'home'; 

    /**
     * Set the active view-state.
     * 
     * @param which which view-state is now active
     */
    function SetActive(which) {
        $log.debug(tag + 'setActive(which=' + which + ')');
        active = which;
    };

    /**
     * Get the active view-state.
     */
    function GetActive() {
        return active;
    };

};

module.exports = NavigationService;
