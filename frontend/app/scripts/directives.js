/* jshint jquery:true */
/* global moment:false */
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
  })
  .directive('cuduSubmenu', function() {
    return {
      restrict: 'A',
      link: function(scope, element) {
        $(element).submenupicker();
      }
    };
  })
  .directive('cuduFecha', function () {
    return {
      restrict: 'A',
      require: 'ngModel',
      scope: { },
      link: function (scope, element, attrs, ngModel) {
        if (!ngModel) {
          return;
        }

        var desempaquetar = function(lista) {
          return _.foldr(lista, function(t, q) {
            return t + '/' + q;
          });
        };

        ngModel.$render = function() {
          element.val(ngModel.$viewValue);
        };

        ngModel.$formatters.push(function(value) {
          if (!value || value.constructor !== Array) {
            return '';
          }
          return desempaquetar(value);
        });

        element.bind('change', function() {
          var fecha = moment(element.val(), ['DDMMYY', 'DDMMYYYY', 'DD-MM-YY', 'DD-MM-YYYY']);
          var empaquetado = [];
          if (fecha.isValid) {
            empaquetado = [fecha.year(), fecha.month() + 1, fecha.date()];
            element.val(desempaquetar(empaquetado));
          }
          // TODO Marcar erronea
          scope.$apply(function() {
            ngModel.$setViewValue(empaquetado);
          });
        });
      }
    };
  });
