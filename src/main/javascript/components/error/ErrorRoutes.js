'use strict';

/**
 * Router configuration for the Error component.
 */
ErrorRoutes.$inject = ['$locationProvider', '$routeProvider'];
function ErrorRoutes($locationProvider, $routeProvider) {

    $routeProvider
        .when('/error', {
            templateUrl: '/components/error/system-error.html',
            title      : 'System Error'
        })
        .when('/not-authorised', {
            templateUrl: '/components/error/not-authorised.html',
            title      : 'Not Authorised'
        });

}

module.exports = ErrorRoutes;
