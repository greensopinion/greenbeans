'use strict';

/**
 * @ngdoc service
 * @name greensopinionfinanceApp.importService
 * @description
 * # importService
 * Service in the greensopinionfinanceApp.
 */
angular.module('greensopinionfinanceApp')
  .service('importService',['rest', function (rest) {
    var API_BASE = '/imports/';
    return {
      importFiles: function() {
          return rest.get(API_BASE+'new');
      }
    };
  }]);
