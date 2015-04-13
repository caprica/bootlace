'use strict';

/**
 * Router configuration for the Profile component.
 */
ProfileRoutes.$inject = ['$locationProvider', '$routeProvider', 'ROLES'];
function ProfileRoutes($locationProvider, $routeProvider, roles) {

    $routeProvider
        .when('/profile', {
            controller   : 'ProfileController',
            controllerAs : 'profile',
            templateUrl  : '/components/profile/profile.html',
            title        : 'Profile',
            active       : 'profile',
            resolve      : require('./ProfileController').resolve,
            authorisation: {
                requires: roles.user
            }
        });

}

module.exports = ProfileRoutes;
