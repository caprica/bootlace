'use strict';

/**
 * Module definition for the Security component.
 */
module.exports = angular.module('bootlace.security', [])
    .config    (                         require('./HttpProviderConfig'           ))
    .config    (                         require('./LoginRoutes'                  ))
    .constant  ('AUTH_EVENTS'          , require('./AuthEvents'                   ))
    .constant  ('ROLES'                , require('./Roles'                        ))
    .controller('LoginController'      , require('./LoginController'              ))
    .controller('SessionController'    , require('./SessionController'            ))
    .factory   ('AuthInterceptor'      , require('./AuthInterceptor'              ))
    .factory   ('AuthorisationService' , require('./AuthorisationService'         ))
    .provider  ('SecurityConfiguration', require('./SecurityConfigurationProvider'))
    .run       (                         require('./RouteChangeHandler'           ))
    .service   ('Session'              , require('./Session'                      ))
;
