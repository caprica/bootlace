'use strict';

/**
 * Session singleton.
 * 
 * This implementation expects a Spring Security Principal, from which the username and the set
 * of role authorisations are retrieved.
 * 
 * @param $log
 * @param CacheFactory
 * @param config
 */
Session.$inject = ['$log', 'CacheFactory', 'SecurityConfiguration'];
function Session($log, CacheFactory, config) {

    var tag = '             Session: ';

    $log.debug(tag + 'Session()');

    /**
     * Create a cache for the user details, to persist across sessions and page refreshes.
     * 
     * This service is a singleton, therefore the cache is created once here during
     * initialisation. 
     */
    var authCache = CacheFactory(config.cacheName, {'storageMode': 'localStorage'});

    /**
     * Private helper method to convert authorities to roles.
     * 
     * @param authorities array of authority objects
     * @return array of roles
     */
    var convertRoles = function(authorities) {
        var roles = [];
        authorities.forEach(function(authority) {
            roles.push(authority.authority);
        });
        return roles;
    };

    /**
     * Create a new user session.
     * 
     * This extracts the username and the set of authorised roles from the user and stores them
     * in this client-side session.
     * 
     * The user entity passed in is also stored in a local storage cache.
     * 
     * @param user user entity
     * @return user supplied user, for convenience
     */
    this.create = function(user) {
        $log.debug(tag + 'create(user=' + user + ')');
        this.sessionId = user.details.sessionId;
        this.userId    = user.name;
        this.userRoles = convertRoles(user.authorities);
        authCache.put(config.userCacheKey, user);
        return user;
    };

    /**
     * Destroy the current user session.
     * 
     * This clears the current username and set of authorised roles from this client-side
     * session and also removes the user entity from the local storage cache.
     */
    this.destroy = function() {
        $log.debug(tag + 'destroy()');
        authCache.remove(config.userCacheKey);
        this.sessionId = null;
        this.userId    = null;
        this.userRoles = null;
    };

    /**
     * Does the current user have the specified role?
     * 
     * @param role role to check
     * @return true only if there is a currently authenticated user and that user has the specified role
     */
    this.hasRole = function(role) {
        $log.debug(tag + 'hasRole(role=' + role + ')');
        $log.debug(tag + 'userRoles=' + this.userRoles);
        var hasRole;
        if (this.userRoles) {
            hasRole = this.userRoles.indexOf(role) !== -1;
        }
        else { 
            hasRole = false;
        }
        $log.debug(tag + 'hasRole=' + hasRole);
        return hasRole;
    };

    // Recreate the user session information if there is a cached user record - this is only part
    // of the story as the SessionController must also take steps to restore the previous user
    // session
    var cachedUser = authCache.get(config.userCacheKey);
    $log.debug(tag + 'cachedUser=' + cachedUser);
    if (cachedUser) {
        $log.debug(tag + 'recreate user session');
        this.create(cachedUser);
    }
    else {
        $log.debug(tag + 'no user session to recreate');
    }

};

module.exports = Session;
