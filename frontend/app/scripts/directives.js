'use strict';

angular.module('cuduApp')
  .directive('cuduModal', function() {
    return {
      restrict: 'A',
      link: function(scope, element, attrs) {
        scope.$watch(attrs.cuduModal, function(value) {
          if (value) {
            element.modal('show');
          } else {
            element.modal('hide');
          }
        });
      }
    };
  });