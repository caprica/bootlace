'use strict';

/**
 * Configuration for the "toastr" notifications module.
 */
ToastrConfiguration.$inject = ['toastrConfig'];
function ToastrConfiguration(toastrConfig) {

    angular.extend(toastrConfig, {
        timeOut: 3000
    });

};

module.exports = ToastrConfiguration;
