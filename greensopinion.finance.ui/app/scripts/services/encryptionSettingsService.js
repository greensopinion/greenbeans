'use strict';

angular.module('greensopinionfinanceApp')
  .service('encryptionSettingsService',['rest',function(rest) {
    var API_BASE = '/encryption-settings/';
    return {
      get: function() {
          return rest.get(API_BASE+'current');
      }
    };
  }]);
