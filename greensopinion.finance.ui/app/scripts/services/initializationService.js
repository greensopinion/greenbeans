/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
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
