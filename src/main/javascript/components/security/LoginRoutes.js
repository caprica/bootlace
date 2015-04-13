'use strict';

/**
 * Router configuration for the Login component.
 */
LoginRoutes.$inject = ['$locationProvider', '$routeProvider'];
function LoginRoutes($locationProvider, $routeProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
        .when('/login', {
            controller  : 'LoginController',
            controllerAs: 'login',
            templateUrl : '/components/login/login.html',
            title       : 'Login',
            active      : 'login'
        });

}

module.exports = LoginRoutes;
