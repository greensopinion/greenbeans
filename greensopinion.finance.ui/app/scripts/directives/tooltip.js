'use strict';

/**
 * @ngdoc tooltip
 * @name greensopinionfinanceApp.directive:tooltip
 * @description
 * # tooltip
 */
angular.module('greensopinionfinanceApp')
  .directive('tooltip', function () {
    return {
      template: '&nbsp;<i class="fa fa-question-circle" popover="{{text}}" popover-trigger="mouseenter" popover-placement="top"></i>',
      restrict: 'E',
      scope: {
        text: '@'
      }
    };
  });
