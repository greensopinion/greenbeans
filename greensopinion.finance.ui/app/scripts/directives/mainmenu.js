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
          label: 'About',
          path: '/about',
          insecure: true
        }];
      }
    };
  }]);
