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
  })
  .directive('cuduTimeStamp', function () {
    return {
      restrict: 'A',
      require: 'ngModel',
      scope: { },
      link: function (scope, element, attrs, ngModel) {
        if (!ngModel) {
          return;
        }

        var desempaquetar = function(timeStamp) {
          return moment(timeStamp).format('D/M/YYYY');
        };

        ngModel.$render = function() {
          element.val(ngModel.$viewValue);
        };

        ngModel.$formatters.push(function(value) {
          if(!(value && value.constructor === Number)) {
            return '';
          }
          return desempaquetar(value);
        });

        element.bind('change', function(e) {
          var fecha = moment(element.val(), ['DDMMYY', 'DDMMYYYY', 'DD-MM-YY', 'DD-MM-YYYY']);
          var empaquetado = 0;
          if (fecha.isValid) {
            empaquetado = fecha.unix()*1000;
            element.val(desempaquetar(empaquetado));
          }
          // TODO Marcar erronea
          scope.$apply(function() {
            ngModel.$setViewValue(empaquetado);
          });
        });
      }
    };
  })
  .directive('rating', function() {
    return {
      restrict: 'E',
      replace: 'true',
      scope: {
        score: "&score",
      },
      link: function(scope, elem, attrs) {
        var score = scope.score();
        if (score == null) {
          // score = -1; para producir empty stars
          return;
        }
        var result = '';
        var relscore = Math.min(4, score);
        for (var i = 0; i <= relscore; i++) { result += '<i class="fa fa-star"></i>'; }
        for (var i = 0; i < 4 - relscore; i++) { result += '<i class="fa fa-star-o"></i>'; }
        elem[0].innerHTML = result;
      }
    };
  })
  .directive('cuduEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.cuduEnter);
                });

                event.preventDefault();
            }
        });
    };
});;
