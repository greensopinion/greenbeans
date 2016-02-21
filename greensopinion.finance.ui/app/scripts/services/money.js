/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

/**
 * @ngdoc service
 * @name greensopinionfinanceApp.money
 * @description
 * # money
 * Service in the greensopinionfinanceApp.
 */
angular.module('greensopinionfinanceApp')
  .service('money', function () {
    return {
      format: function(value) {
        var absValue = Math.abs(value);
        var floatValue = absValue/100.0;
        return (value<0?'-':'')+ floatValue.toFixed(2);
      }
    };
  });
