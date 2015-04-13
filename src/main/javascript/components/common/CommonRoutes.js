'use strict';

/**
 * Router configuration for the Common component.
 * 
 * This configuration sets HTML5 mode and a default route.
 */
CommonRoutes.$inject = ['$locationProvider', '$routeProvider'];
function CommonRoutes($locationProvider, $routeProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
        .otherwise({
            redirectTo: function () {
                return '/home';
            }
        });

};

module.exports = CommonRoutes;
