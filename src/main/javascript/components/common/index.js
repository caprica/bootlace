'use strict';

/**
 * Module definition for the Common component.
 */
module.exports = angular.module('bootlace.common', [])
    .config    (                  require('./CommonRoutes'         ))
    .config    (                  require('./ToastrConfiguration'  ))
    .directive ('autofocus'     , require('./AutofocusDirective'   ))
    .directive ('isoDate'       , require('./ISODateDirective'     ))
    .directive ('year'          , require('./YearDirective'        ))
    .run       (                  require('./RouteChangeSuccess'   ))
;
