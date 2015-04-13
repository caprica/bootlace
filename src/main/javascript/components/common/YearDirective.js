'use strict';

/**
 * Simple directive that inserts the current year.
 * 
 * Usage example:
 * 
 * <footer>
 *     &copy; <year></year> <a href="http://capricasoftware.co.uk">Caprica Software Limited</a> 
 * </footer>
 * 
 * This directive can be used as an element in its own right, or as an attribute on an element.
 */
function YearDirective() {

    var moment = require('moment');
    
    return {
        restrict: 'EA',
        template: '{{year}}',
        link: function(scope, element, attrs) {
            scope.year = new moment().year();
        }
    };

};

module.exports = YearDirective;
