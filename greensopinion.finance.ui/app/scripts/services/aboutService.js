/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

angular.module('greensopinionfinanceApp')
  .service('aboutService',['rest',function(rest) {
    var API_BASE = '/abouts/';
    return {
      about: function() {
          return rest.get(API_BASE+'current');
      }
    };
  }]);
