/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
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
    var API_BARE = '/imports';
    var API_BASE = API_BARE+'/';
    return {
      selectedFiles: function() {
          return rest.get(API_BASE+'selected');
      },
      importFiles: function(selectedFiles,deleteAfterImport) {
          return rest.post(API_BARE,{ files: selectedFiles, deleteAfterImport: deleteAfterImport });
      }
    };
  }]);
