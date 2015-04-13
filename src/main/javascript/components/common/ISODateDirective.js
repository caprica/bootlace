'use strict';

function ISODateDirective() {

    return {
        restrict: 'EA',
        require: 'ngModel',
        link: function(scope, element, attrs, ngModelController) {
//            ngModelController.$parsers.push(function(data) { // FIXME don't really need this but should implement it for completeness
//                return data;
//            });
            ngModelController.$formatters.push(function(data) {
                return data ? moment(data).toDate() : null;
            });
        }
    }

};

module.exports = ISODateDirective;
