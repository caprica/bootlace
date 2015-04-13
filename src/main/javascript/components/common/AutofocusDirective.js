'use strict';

/**
 * Implementation of a directive that requests focus to the associated element.
 * 
 * Usage example:
 * 
 * <input autofocus id="email" type="email" ng-model="model.email" name="email">
 */
AutofocusDirective.$inject = ['$timeout'];
function AutofocusDirective($timeout) {

    return { 
        restrict: 'A',
        link: function(scope, element, attr) {
            $timeout(function() {
                element[0].focus();
            }, 0, false);
        }
    };
    
};

module.exports = AutofocusDirective;
