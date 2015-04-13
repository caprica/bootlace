'use strict';

/**
 * User authentication session controller. 
 *
 * This session controller is the only component responsible for creating a new authenticated
 * user session, or destroying that session when the user logs out or is somehow otherwise no
 * longer authenticated.
 * 
 * This controller is also responsible for reconstructing the previously cached authenticated
 * user which is useful e.g. if loading a new page in a new browser tab or window - otherwise
 * that is basically a page refresh and any existing authenticated user and authorised roles
 * would be lost. 
 * 
 * This controller should be attached to a top-level element (e.g. "body") or some other
 * element outside of the "ng-view".
 * 
 * @param $log
 * @param $scope
 * @param $location
 * @param Authorisationservice
 * @param CacheFactory
 * @param Session
 * @param authEvents
 * @param roles
 * @param config
 * @param toastr
 */
SessionController.$inject = ['$log', '$scope', '$location', 'AuthorisationService', 'CacheFactory', 'Session', 'AUTH_EVENTS', 'ROLES', 'SecurityConfiguration', 'toastr'];
function SessionController($log, $scope, $location, AuthorisationService, CacheFactory, Session, authEvents, roles, config, toastr) {

    var tag = '   SessionController: ';

    $log.debug(tag + 'SessionController()');
    
    // Make various authentication and authorisation objects available for use in templates
    $scope.currentUser        = null;
    $scope.roles              = roles;
    $scope.isAuthorisedFor    = AuthorisationService.isAuthorisedForAny; // Intentional synonym
    $scope.isAuthorisedForAny = AuthorisationService.isAuthorisedForAny;
    $scope.isAuthorisedForAll = AuthorisationService.isAuthorisedForAll;

    /**
     * Private method to clear the currently authenticated user.
     * 
     * This is factored out to a separate function rather than just using
     * "setCurrentUser(null)" so that it can be used directly as an event handler callback.
     */
    function clearUser() {
        $log.debug(tag + 'clearUser()');
        $scope.setCurrentUser(null);
    };

    /**
     * Set (or clear) the current user.
     * 
     * If a user is provided it is stored in the controller scope and is therefore accessible
     * by all child scopes (e.g. templates in nested controllers). Additionally a new
     * client-side session will be created.
     * 
     * If a user is not provided, then the existing user will be removed from the controller
     * scope and the client-side session will be destroyed. 
     * 
     * @param user user, or null to clear the current user
     */
    $scope.setCurrentUser = function(user) {
        $log.debug(tag + 'setCurrentUser(user=' + user + ')');
        $scope.currentUser = user;
        if (user) {
            Session.create(user);
        }
        else {
            Session.destroy();
        }
    };

    /**
     * Logout.
     *
     * Three particular actions are required on logout: 
     * 
     * First, the authorisation service will destroy the user session instance and remove it
     * from the local storage cache.
     * 
     * Second, the current user will be cleared from the session controller scope.
     * 
     * Third, the router changes to the post-logout view state (or an error state if for some
     * reason the logout fails).
     */
    $scope.logout = function() {
        $log.debug(tag + 'logout()');
        AuthorisationService
            .logout()
            .then(
                function() {
                    $log.debug(tag + 'logout success');
                    $location.path(config.afterLogoutState);
                    toastr.success('Logged out successfully');
                },
                function() {
                    $log.debug(tag + 'logout failed');
                    // Clear the view-state parameter, otherwise it will propagate to the next view
                    // and remain visible in the browser address bar
                    $location.search(config.afterLoginParameter, null);
                    $location.path(config.errorState);
                }
            );
    };

    // Clear the current user on a logout
    $scope.$on(authEvents.logoutSuccess, clearUser);
    
    // Clear the current user on a 'not authenticated' event (this can happen if the server
    // security context is no longer valid, e.g. by a session timeout or by logging out from a
    // different browser tab/window)
    $scope.$on(authEvents.notAuthenticated, clearUser);

    // Attempt to restore a previously authenticated user - this is the other part of the story
    // that started with the initialisation of the Session singleton
    var authCache = CacheFactory.get(config.cacheName);
    var cachedUser = authCache.get(config.userCacheKey);
    $log.debug(tag + 'cachedUser=' + cachedUser);
    if (cachedUser) {
        $log.debug(tag + 'set cached user');
        $scope.setCurrentUser(cachedUser);
    }
    else {
        $log.debug(tag + 'no cached user to set');
    }

};

module.exports = SessionController;
