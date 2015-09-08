'use strict';

angular.module('greensopinionfinanceApp')
  .service('encryptionSettingsService',['rest',function(rest) {
    var API_BASE = '/encryption-settings/';
    return {
      get: function() {
          return rest.get(API_BASE+'current');
      },
      configureMasterPassword: function(masterPassword) {
          var entity = { masterPassword: masterPassword };
          return rest.put(API_BASE+'current',entity);
      },
      initializeMasterPassword: function(masterPassword) {
          var entity = { masterPassword: masterPassword };
          return rest.post(API_BASE+'current',entity);
      }
    };
  }]);
