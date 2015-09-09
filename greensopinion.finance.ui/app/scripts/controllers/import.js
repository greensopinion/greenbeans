'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:ImportctrlCtrl
 * @description
 * # ImportctrlCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('ImportCtrl',['$scope','errorService','importService', function ($scope,errorService,importService) {
    $scope.importFiles = function() {
      return errorService.maintainErrorMessageInScope(importService.importFiles(),$scope);
    };
  }]);
