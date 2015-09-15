'use strict';

/**
 * @ngdoc service
 * @name greensopinionfinanceApp.initializationService
 * @description
 * # initializationService
 * Service in the greensopinionfinanceApp.
 */
angular.module('greensopinionfinanceApp')
  .service('initializationService', function () {
    var initialized = false;
    return {
      isInitialized: function() {
        return initialized;
      },
      initialized: function(value) {
        initialized = value;
      }
    };
  });
