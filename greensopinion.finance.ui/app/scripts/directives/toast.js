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
