'use strict';

/**
 * Router configuration for the About component.
 */
HomeRoutes.$inject = ['$locationProvider', '$routeProvider'];
function HomeRoutes($locationProvider, $routeProvider) {

    $routeProvider
        .when('/about', {
            controller   : 'AboutController',
            controllerAs : 'about',
            templateUrl  : '/components/about/about.html',
            title        : 'About',
            active       : 'about'
        });

}

module.exports = HomeRoutes;
