'use strict';

/**
 * Implementation of an authorisation service.
 * 
 * @param $log
 * @param $http
 * @param $q
 * @param $rootScope
 * @param authEvents
 * @param config
 * @param Session
 */
AuthorisationService.$inject = ['$log', '$http', '$q', '$rootScope', 'AUTH_EVENTS', 'SecurityConfiguration', 'Session'];
function AuthorisationService($log, $http, $q, $rootScope, authEvents, config, Session) {

    var tag = 'AuthorisationService: ';

    $log.debug(tag + 'AuthorisationService()');

    var authorisationService = {};

    /**
     * Format a standard HTTP "Basic" authentication header value.
     * 
     * The header value has a specific format, i.e. the literal string 'Basic ' followed by the
     * Base64 encoding of the concatenation of username and password, separated by ':'.
     * 
     * This is a private method.
     * 
     * @param credentials authentication credentials containing username and password
     */
    var basicHeader = function(credentials) {
        return 'Basic ' + btoa(credentials.username + ':' + credentials.password);
    };

    /**
     * Attempt to login with credentials.
     * 
     * This implementation sets an HTTP Basic authorisation header and attempts to GET a
     * secured user resource on the server. If the authentication works a new session will be
     * created on the server and the client Session singleton will be updated with the
     * authenticated user values.
     * 
     * The makeup of the returned user resource is dependent on the back-end implementation, it
     * will subsequently be 'unpacked' and used to create the client-side session.  
     * 
     * On a successful login, the authenticated user will be returned.
     * 
     * There are two ways a login failure can occur - either an authentication failure (wrong
     * username/password) or an actual error when making the HTTP request. The component that
     * invokes login can add its own error handler which can handle both of these conditions by
     * checking the value of a single boolean parameter passed to it - true for an authentication
     * failure and false for a technical failure.
     * 
     * @param credentials authentication credentials containing username and password
     */
    authorisationService.login = function(credentials) {
        $log.debug(tag + 'login()');
        var headers = credentials ? {Authorization: basicHeader(credentials)} : {};
        return $http
            .get(config.authenticationUrl, {headers: headers})
            .then(
                function(result) {
                    $log.debug(tag + 'login succeeded');
                    return result.data;
                },
                function(response) {
                    $log.debug(tag + 'login failed ' + response.status);
                    return $q.reject(response.status == 401);
                }
            );
    };

    /**
     * Force a logout.
     * 
     * This implementation POSTs an empty request to a logout resource on the server.
     */
    authorisationService.logout = function() {
        $log.debug(tag + 'logout()');
        return $http
            .post(config.logoutUrl, {})
            .then(
                function() {
                    $log.debug(tag + 'logout succeeded');
                    $rootScope.$broadcast(authEvents.logoutSuccess);
                }
            );
    };

    /**
     * Is there a currently authenticated user session?
     * 
     * @return true if there is an authenticated user; false if there is not
     */
    authorisationService.isAuthenticated = function() {
        $log.debug(tag + 'isAuthenticated()');
        var authenticated = !!Session.userId;
        $log.debug(tag + 'authenticated=' + authenticated);
        return authenticated;
    };

    /**
     * Private helper method to get one or more roles as an array of roles.
     * 
     * Having an array in all circumstances simplifies role-checking later.
     * 
     * @param roles a single role, or an array
     * @return array of roles
     */
    var getRoles = function(roles) {
        return angular.isArray(requiredRoles) ? roles : [roles];
    };

    /**
     * Check an array of roles against those of the currently authenticated user.
     * 
     * This is a private method, used by the isAuthorisedXXX() functions.
     * 
     * @param roles roles to check
     * @param checker checker function
     * @return true only if there is an authenticated user, and the roles for that user satisfy the checker function; otherwise false
     */
    var checkRoles = function(roles, checker) {
        $log.debug(tag + 'checkRoles(roles=' + roles + ')');
        var authorised;
        if (authorisationService.isAuthenticated()) {
            authorised = checker(angular.isArray(roles) ? roles : [roles]);  
        }
        else {
            authorised = false;
        }
        $log.debug(tag + 'authorised=' + authorised);
        return authorised;
    };

    /**
     * Check if the currently authenticated user has at least one of the demanded roles.
     * 
     * @param requiredRoles roles to check
     * @return true only if there is an authenticated user, and that user has any of the demanded roles
     */
    authorisationService.isAuthorisedForAny = function(roles) {
        $log.debug(tag + 'isAuthorisedForAny(roles=' + roles + ')');
        var authorisedForAny = checkRoles(roles, function(anyOf) {
            return anyOf.some(function(role) {
                return Session.hasRole(role);
            });
        });
        $log.debug(tag + 'authorisedForAny=' + authorisedForAny);
        return authorisedForAny;
    };

    /**
     * Check if the currently authenticated user has all of the demanded roles.
     * 
     * @param requiredRoles roles to check
     * @return true only if there is an authenticated user, and that user has all of the demanded roles
     */
    authorisationService.isAuthorisedForAll = function(roles) {
        $log.debug(tag + 'isAuthorisedForAll(roles=' + roles + ')');
        var authorisedForAll = checkRoles(roles, function(allOf) {
            return allOf.every(function(role) {
                return Session.hasRole(role);
            });
        });
        $log.debug(tag + 'authorisedForAll=' + authorisedForAll);
        return authorisedForAll;
    };

    return authorisationService;

};

module.exports = AuthorisationService;
