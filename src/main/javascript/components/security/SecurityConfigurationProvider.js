'use strict';

// FIXME not sure this is the best way to do configuration, currently its not used
// FIXME just put roles in here too?

/**
 * Security module configuration provider.
 * 
 * This configuration specifies constant values that are reasonably expected to be configurable
 * depending on a particular application and the associated back-end authentication and
 * authorisation web-services.
 * 
 * Sensible defaults are provided.
 */
function SecurityConfigurationProvider() {

    /**
     * Configuration with defaults.
     */
    var config = {

        /**
         * Name of the cache that is used for the authenticated user details. 
         */
        cacheName          : 'authCache',

        /**
         * Name of the cache key under which the authenticated user details are stored.
         */
        userCacheKey       : 'user',

        /**
         * URL of the web-service for the user authentication resource, this is effectively a
         * "login" * when valid credentials are supplied.
         * 
         * A GET request is issued to this secured URL end-point to establish the server
         * security context and return authenticated user details.
         */
        authenticationUrl  : '/user',

        /**
         * URL of the web-service used to logout the currently authenticated user.  
         */
        logoutUrl          : '/logout',

        /**
         * View state to change to when a login is required.
         */
        loginState         : '/login',

        /**
         * Default view state to change to after a successful login.
         */
        afterLoginState    : '/home',

        /**
         * Name of the request parameter that is used to set the new view state after a
         * successful login.
         * 
         * Use of this parameter enables switching to the originally requested view state after
         * a successful login (e.g. as a result of deep-linking).
         * 
         * This configuration parameter is optional, if it is not set then after a login the
         * view-state will always change to the configured 'afterLoginState' value.
         * 
         * This request parameter and the target view state value will be visible in the
         * browser address bar while on the login page - this is unavoidable.
         */
        afterLoginParameter: 'then',

        /**
         * View state to change to after a successful logout.
         */
        afterLogoutState   : '/home',

        /**
         * View state to change to when the current user requests a resource that they are not
         * authorised to access.
         */
        notAuthorisedState : '/not-authorised',

        /**
         * View state to change to when some other unhandled error response is returned.
         * 
         * This is for e.g. 500 errors rather than authorisation errors.
         */
        errorState         : '/error' // FIXME maybe a dedicated 404 error page too?

    };

    this.cacheName = function(value) {
        config.cacheName = value;
    };
    
    this.userCacheKey = function(value) {
        config.userCacheKey = value;
    };

    this.authenticationUrl = function(value) {
        config.authenticationUrl = value;
    };

    this.logoutUrl = function(value) {
        config.logoutUrl = value;
    };

    this.loginState = function(value) {
        config.loginState = value;
    };

    this.afterLoginState = function(value) {
        config.afterLoginState = value;
    };

    this.afterLoginParameter = function(value) {
        config.afterLoginParameter = value;
    };

    this.afterLogoutState = function(value) {
        config.afterLogoutState = value;
    };

    this.notAuthorisedState = function(value) {
        config.notAuthorisedState = value;
    };

    this.errorState = function(value) {
        config.errorState = value;
    };

    this.$get = function configProviderFactory() {
        return config;
    };

};

module.exports = SecurityConfigurationProvider;
