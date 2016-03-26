/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
