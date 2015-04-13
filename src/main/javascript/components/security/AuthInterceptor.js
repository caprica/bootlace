'use strict';

/**
 * Response interceptor implementation.
 * 
 * This implementation maps an HTTP authentication error response to an application event and
 * broadcasts the event.
 * 
 * If the error response was due to an authentication or authorisation failure, the view-state
 * is changed to demand a new login. The requested view-state is remembered and passed along so
 * after a successful login it may be restored,
 * 
 * @param $log
 * @param $rootScope
 * @param $q
 * @param $location
 * @param authEvents
 * @param config 
 */
AuthInterceptor.$inject = ['$log', '$rootScope', '$q', '$location', 'AUTH_EVENTS', 'SecurityConfiguration'];
function AuthInterceptor($log, $rootScope, $q, $location, authEvents, config) {

    var tag = '     AuthInterceptor: ';

    /**
     * Map of HTTP response codes to application events.
     */
    var eventMap = {
        401: authEvents.notAuthenticated,
        403: authEvents.notAuthorised
    };
    
    return {
        responseError: function(response) {
            $log.debug(tag + 'responseError ' + response.status);
            $rootScope.lastErrorResponse = response.data; // FIXME like this, or a service or something? this is reasonable, but if you e.g. refresh the error page you will lose the rootscope var could use cache in service instead a bit OTT maybe
            var event = eventMap[response.status];
            $log.debug(tag + 'event=' + event);
            if (event) {
                $log.debug(tag + 'auth error');
                $rootScope.$broadcast(eventMap[response.status], response);
                // Special case if the current view-state is already the login state
                var atLoginState = $location.path() == config.loginState
                $log.debug(tag + 'atLoginState=' + atLoginState);
                // If the view-state is not the login state, then switch the view-state to login...
                if (!atLoginState) {
                    // Change the view-state to the login state
                    var loginUrl = config.loginState;
                    // If the configuration says so, pass along the originally requested path as a
                    // request parameter so it can be restored after a successful login
                    if (config.afterLoginParameter) {
                        // The leading '/' in the path is stripped to make the resultant URL in the
                        // address bar slightly less ugly, it is added back at the appropriate time
                        // in the LoginController
                        loginUrl += '?' + config.afterLoginParameter + '=' + $location.path().substring(1);
                    }
                    $log.debug(tag + 'loginUrl=' + loginUrl);
                    $location.url(loginUrl).replace();
                }
            }
            else {
                $log.debug(tag + 'non-auth error');
                // Clear the view-state parameter, otherwise it will propagate to the next view and
                // remain visible in the browser address bar
                $location.search(config.afterLoginParameter, null);
                $location.path(config.errorState).replace(); // FIXME not sure about replace() here... e.g. save a form, error, the history of the form page view is lost                
            }
            return $q.reject(response);
        }
    };

};

module.exports = AuthInterceptor;
