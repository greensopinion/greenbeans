'use strict';

/**
 * @ngdoc directive
 * @name greensopinionfinanceApp.directive:mainmenu
 * @description
 * # mainmenu
 */
angular.module('greensopinionfinanceApp')
  .directive('mainmenu',['$location', function ($location) {
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
        scope.menuElements = [
          {
            label: 'Home',
            path: '/'
          },
          {
            label: 'Import',
            path: '/import'
          },
          {
            label: 'About',
            path: '/about'
          }
        ];
      }
    };
  }]);
