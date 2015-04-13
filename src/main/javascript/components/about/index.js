'use strict';

/**
 * Module definition for the About component.
 */
module.exports = angular.module('bootlace.about', [])
    .config    (                    require('./AboutRoutes'     ))
    .controller('AboutController' , require('./AboutController' ))
;
