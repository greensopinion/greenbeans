'use strict';

/**
 * @ngdoc directive
 * @name greensopinionfinanceApp.directive:mainmenu
 * @description
 * # mainmenu
 */
angular.module('greensopinionfinanceApp')
  .directive('mainmenu',['$location','initializationService', function ($location,initializationService) {
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
          if (!initializationService.isInitialized()) {
              return element.insecure === true;
          } else if (element.path === '/') {
              return false;
          }
          return true;
        };
        scope.menuElements = [
          {
            label: 'Secure Your Data',
            path: '/',
            insecure: true
          },
          {
            label: 'Transactions',
            path: '/transactions'
          },
          {
            label: 'Categories',
            path: '/categories'
          },
          {
            label: 'Import',
            path: '/import'
          },
          {
            label: 'About',
            path: '/about',
            insecure: true
          }
        ];
      }
    };
  }]);
