/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:ImportctrlCtrl
 * @description
 * # ImportctrlCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('ImportCtrl',['$scope','errorService','importService','toastService', function ($scope,errorService,importService,toastService) {

    var resetSelectedFiles = function() {
      errorService.clearErrorMessage($scope);
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
          if ($scope.files.length === 1) {
            toastService.show('Successfully imported '+$scope.files[0]);
          } else {
            toastService.show('Successfully imported '+$scope.files.length+' files.');
          }
          resetSelectedFiles();
        });
    };
    resetSelectedFiles();
  }]);
