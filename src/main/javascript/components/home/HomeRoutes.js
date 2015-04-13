'use strict';

/**
 * Router configuration for the Home component.
 */
HomeRoutes.$inject = ['$locationProvider', '$routeProvider'];
function HomeRoutes($locationProvider, $routeProvider) {

    $routeProvider
        .when('/home', {
            controller   : 'HomeController',
            controllerAs : 'home',
            templateUrl  : '/components/home/home.html',
            title        : 'Home',
            active       : 'home'
        });

}

module.exports = HomeRoutes;
