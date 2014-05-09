'use strict';

var cuduServices = angular.module('cuduServices', ['ngResource']);
 
cuduServices.factory('Asociado', ['$resource',
  function($resource){
    return $resource('asociado/:idAsociado', {}, {
      query: { method: 'GET', params: { }, isArray: true }
    });
  }]);