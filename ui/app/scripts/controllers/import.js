/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
