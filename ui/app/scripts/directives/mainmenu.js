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
 * @name greensopinionfinanceApp.directive:mainmenu
 * @description
 * # mainmenu
 */
angular.module('greensopinionfinanceApp')
  .directive('mainmenu', ['$location', 'initializationService', 'eulaService', function($location,
    initializationService, eulaService) {
    return {
      templateUrl: 'views/directives/mainmenu.html',
      restrict: 'E',
      link: function postLink(scope) {
        scope.getClass = function(path) {
          if (path !== '/' && $location.path().substr(0, path.length) === path) {
            return 'active';
          }
          if ($location.path() === path) {
            return 'active';
          }
          return '';
        };
        scope.isVisible = function(element) {
          if (!eulaService.isEulaCheckComplete()) {
            return false;
          }
          if (!initializationService.isInitialized()) {
            return element.insecure === true;
          } else if (element.path === '/encryption') {
            return false;
          }
          return true;
        };
        scope.menuElements = [{
          label: 'Secure Your Data',
          path: '/encryption',
          insecure: true
        }, {
          label: 'Reports',
          path: '/reports'
        }, {
          label: 'Transactions',
          path: '/transactions'
        }, {
          label: 'Categories',
          path: '/categories'
        }, {
          label: 'Import',
          path: '/import'
        }, {
          label: 'Help',
          path: '/help',
          insecure: true
        }, {
          label: 'About',
          path: '/about',
          insecure: true
        }
        ];
      }
    };
  }]);
