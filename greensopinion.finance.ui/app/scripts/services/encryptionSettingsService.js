/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

angular.module('greensopinionfinanceApp')
  .service('encryptionSettingsService', ['rest', function(rest) {
    var API_BASE = '/encryption-settings';
    return {
      get: function() {
        return rest.get(API_BASE + '/current');
      },
      configureMasterPassword: function(masterPassword) {
        var entity = {
          masterPassword: masterPassword
        };
        return rest.put(API_BASE + '/current', entity);
      },
      initializeMasterPassword: function(masterPassword) {
        var entity = {
          masterPassword: masterPassword
        };
        return rest.post(API_BASE + '/current', entity);
      },
      resetMasterPassword: function(masterPassword,newMasterPassword) {
        var entity = {
          masterPassword: masterPassword,
          newMasterPassword: newMasterPassword
        };
        return rest.put(API_BASE , entity);
      }
    };
  }]);
