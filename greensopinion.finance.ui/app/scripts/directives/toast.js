/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

/**
 * @ngdoc directive
 * @name greensopinionfinanceApp.directive:toast
 * @description
 * # toast
 */
angular.module('greensopinionfinanceApp')
  .directive('toast', ['toastService', function (toastService) {
    return {
      templateUrl: 'views/directives/toast.html',
      restrict: 'E',
      link: function postLink(scope) {
        scope.toastMessage = function() {
          return toastService.message();
        };
      }
    };
  }]);
