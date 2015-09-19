'use strict';

/**
 * @ngdoc service
 * @name greensopinionfinanceApp.categoryService
 * @description
 * # categoryService
 * Service in the greensopinionfinanceApp.
 */
angular.module('greensopinionfinanceApp')
  .service('categoryService', ['rest', function(rest) {
    var API_BASE = '/categories';
    return {
      list: function() {
          return rest.get(API_BASE);
      }
    };
  }]);
