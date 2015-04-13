'use strict';

/**
 * During application startup, install watches for AngularJS route changes to apply user
 * authorisation checks. 
 * 
 * @param $log
 * @param $rootScope
 * @param $location
 * @param AuthorisationService
 * @param authEvents
 * @param config
 */
RouteChangeHandler.$inject = ['$log', '$rootScope', '$location', 'AuthorisationService', 'AUTH_EVENTS', 'SecurityConfiguration']; 
function RouteChangeHandler($log, $rootScope, $location, AuthorisationService, authEvents, config) {

    var tag = '         RouteChange: ';

    $log.debug(tag + 'run()');

    $rootScope.$on('$routeChangeStart', function(event, next) {
        $log.debug(tag + '----------------------------------------');
        $log.debug(tag + 'routeChangeStart(path=' + next.originalPath + ')');
        var authorisation = next.authorisation;
        $log.debug(tag + 'authorisation=' + authorisation);
        if (authorisation) {
            $log.debug(tag + 'checking route authorisation');
            var authorised;
            // If requires a single role...
            if (authorisation.requires) {
                var requires = authorisation.requires;
                $log.debug(tag + 'requires=' + requires);
                authorised = AuthorisationService.isAuthorisedForAny(requires);
            }
            // If requires at least one from a set of roles...
            else if (authorisation.requiresAnyOf) {
                var requiresAny = authorisation.requiresAnyOf;
                $log.debug(tag + 'requiresAny=' + requires);
                authorised = AuthorisationService.isAuthorisedForAny(requiresAny);
            }
            // If requires all of the roles in a set...
            else if (authorisation.requiresAllOf) {
                var requiresAll = authorisation.requiresAllOf;
                $log.debug(tag + 'requiresAll=' + requires);
                authorised = AuthorisationService.isAuthorisedForAll(requiresAll);
            }
            // Otherwise no authorisation requirements...
            else {
                $log.debug(tag + 'no requires');
                authorised = true;
            }
            $log.debug(tag + 'authorised=' + authorised);
            if (!authorised) {
                event.preventDefault();
                // If there is an authenticated user, then it must be the authorisation that failed 
                if (AuthorisationService.isAuthenticated()) {
                    $log.debug(tag + 'Not authorised');
                    $rootScope.$broadcast(authEvents.notAuthorised);
                    $location.path(config.notAuthorisedState).replace();
                } 
                // Otherwise there is no authenticated user, so force a login
                else {
                    $log.debug(tag + 'Not authenticated');
                    $rootScope.$broadcast(authEvents.notAuthenticated);
                    // Change the view-state to the login state
                    var loginUrl = config.loginState;
                    // If the configuration says so, pass along the originally requested path as a
                    // request parameter so it can be restored after a successful login
                    if (next.originalPath && config.afterLoginParameter) { 
                        // The leading '/' in the path is stripped to make the resultant URL in
                        // the address bar slightly less ugly, it is added back at the
                        // appropriate time in the LoginController
                        loginUrl += '?' + config.afterLoginParameter + '=' + next.originalPath.substring(1);
                    }
                    $log.debug(tag + 'loginUrl=' + loginUrl);
                    $location.url(loginUrl).replace();                    
                }
            }
        }
        else {
            $log.debug(tag + 'no route authorisation to check');
        }
        $log.debug(tag + '========================================\n');
    });

    $rootScope.$on('$routeChangeError', function(event, next, current) {
        $log.debug(tag + 'routeChangeError');
        // FIXME anything needed here?
    });

};

module.exports = RouteChangeHandler;
