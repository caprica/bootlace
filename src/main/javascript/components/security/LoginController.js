'use strict';

/**
 * Controller for the user login view.
 * 
 * This controller should be attached to an element that contains a login form. That login form
 * needs data-binding for 'username' and 'password' models. 
 * 
 * @param $location
 * @param $rootScope
 * @param $scope
 * @param AuthorisationService
 * @param authEvents
 * @param config
 */
LoginController.$inject = ['$log', '$location', '$rootScope', '$scope', 'AuthorisationService', 'AUTH_EVENTS', 'SecurityConfiguration'];
function LoginController($log, $location, $rootScope, $scope, AuthorisationService, authEvents, config) {

    var tag = '     LoginController: ';
    
    $log.debug(tag + 'LoginController()');

    // Use the 'view-model' pattern for this controller
    var vm = this;

    /**
     * Reset the model credentials.
     */
    function resetCredentials() {
        vm.credentials = {
            username: '',
            password: ''
        };
    };
    
    // Initialise the empty credentials model for data-binding
    resetCredentials();

    /**
     * Perform a login.
     * 
     * On successful login the corresponding event is broadcast, and the logged in user is
     * stored in the scope - specifically it is not stored in the scope for this controller,
     * rather it is stored in the scope of a parent controller where the "setCurrentUser"
     * method is defined. This is marginally better than storing the user in the global
     * $rootScope.
     * 
     * @param credentials login credentials, containing username and password
     */
    vm.login = function(credentials) {
        $log.debug(tag + 'login()');
        AuthorisationService
            .login(credentials)
            .then(
                /**
                 * Login success callback.
                 * 
                 * @param user authenticated user
                 */
                function(user) {
                    $log.debug(tag + 'login success');
                    $log.debug(tag + 'user=' + user);
                    // Clear out any previous failure
                    vm.loginFailure = false;
                    // Set the newly authenticated user - this will also create a new
                    // client-side user session
                    $scope.setCurrentUser(user);
                    // Fire the application event
                    $rootScope.$broadcast(authEvents.loginSuccess);
                    // Check if there is a specific view-state to transition to after the login
                    var afterLogin = $location.search()[config.afterLoginParameter];
                    $log.debug(tag + 'afterLogin=' + afterLogin);
                    // Clear the view-state parameter, otherwise it will propagate to the next
                    // view and remain visible in the browser address bar
                    $location.search(config.afterLoginParameter, null);
                    // If there is a view-state to transition to...
                    if (afterLogin) {
                        $log.debug(tag + 'switch to requested after login state');
                        // The leading '/' that was previously stripped from the path must be
                        // restored here (it was stripped in either RouteChangeHandler or
                        // AuthInterceptor)
                        $location.path('/' + afterLogin).replace();
                    }
                    // No specific post-login view state, so switch to the default
                    else {
                        $log.debug(tag + 'switch to default after login state');
                        $location.path(config.afterLoginState).replace();
                    }
                },
                /**
                 * Login error callback.
                 * 
                 * @param authFailure true if this was an authentication failure; false for a technical failure
                 */
                function(authFailure) {
                    $log.debug(tag + 'login failed ' + (authFailure ? '(authentication)' : '(error)'));
                    $rootScope.$broadcast(authEvents.loginFailed);
                    vm.loginFailure = true;
                    resetCredentials();
                }
            );
    };

};

module.exports = LoginController;
