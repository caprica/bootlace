'use strict';

/**
 * Configure the AngularJS $httpProvider.
 * 
 * @param $httpProvider
 */
HttpProviderConfig.$inject = ['$httpProvider']; 
function HttpProviderConfig($httpProvider) {

    // This will prevent Spring from sending www-authenticate, so the web browser will not then
    // throw up a Basic authentication dialog
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    // Add a response interceptor to handle authentication and authorisation failures on HTTP
    // requests
    $httpProvider.interceptors.push(['$injector', function($injector) {
        return $injector.get('AuthInterceptor');
    }]);

};

module.exports = HttpProviderConfig;
