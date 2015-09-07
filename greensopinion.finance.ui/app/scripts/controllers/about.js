'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('AboutCtrl',['$scope','aboutService', function ($scope,aboutService) {
    aboutService.about().then(function(about) {
      $scope.copyrightNotice = about.copyrightNotice;
      $scope.applicationName = about.applicationName;
    });
  }]);
