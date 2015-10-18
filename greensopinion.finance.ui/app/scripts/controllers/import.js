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

    var resetSelectedFiles = function() {
      $scope.deleteAfterImport = true;
      delete $scope.files;
    };
    $scope.selectFiles = function() {
      return errorService.maintainErrorMessageInScope(importService.selectedFiles(),$scope)
        .then(function(selectedFiles) {
          $scope.files = selectedFiles.files;
        });
    };
    $scope.hasFiles = function() {
      return $scope.files !== undefined && $scope.files.length > 0;
    };
    $scope.cancel = function() {
      resetSelectedFiles();
    };
    $scope.importFiles = function(deleteAfterImport) {
      return errorService.maintainErrorMessageInScope(importService.importFiles($scope.files,deleteAfterImport),$scope)
        .then(function() {
          resetSelectedFiles();
        });
    };
    resetSelectedFiles();
  }]);
