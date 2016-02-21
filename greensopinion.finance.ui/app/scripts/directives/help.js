/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

/**
 * @ngdoc directive
 * @name greensopinionfinanceApp.directive:help
 * @description
 * # help
 */
angular.module('greensopinionfinanceApp')
  .directive('help', function () {
    return {
      template: '<div class="ihelp" ng-class="extraClasses()" ng-mouseenter="show()" ng-mouseleave="hide()">'+
      '<a href="#/help#{{target}}"><i class="fa fa-info-circle"></i><span ng-if="showing" class="text-info"> How To: {{text}}</span></a></div>',
      restrict: 'E',
      link: function postLink(scope, element, attrs) {
        scope.target = attrs.target;
        scope.text = attrs.text;
        scope.showing = false;

        scope.show = function() {
          scope.showing = true;
        };
        scope.hide = function() {
          scope.showing = false;
        };
        scope.extraClasses = function() {
          if (scope.showing) {
            return 'showing';
          }
          return '';
        };
      }
    };
  });
