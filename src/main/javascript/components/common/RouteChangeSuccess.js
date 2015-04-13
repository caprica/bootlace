'use strict';

RouteChangeSuccess.$inject = ['$rootScope', 'NavigationService'];

function RouteChangeSuccess($rootScope, NavigationService) {

    // FIXME maybe pageTitle should be controller/service etc? could even be part of NavigationService actually
    
    $rootScope.$on("$routeChangeSuccess", function(event, currentRoute, previousRoute) {
        $rootScope.pageTitle = currentRoute.title;
        NavigationService.setActive(currentRoute.active);
    });

};

module.exports = RouteChangeSuccess;
