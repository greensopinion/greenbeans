'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('AboutCtrl',['$scope','$window', function ($scope,$window) {
    $scope.copyrightNotice = $window.appServiceLocator.getAboutService().getCopyrightNotice(); // 'Copyright (c) 2015 David Green.  All rights reserved.';
  }]);
