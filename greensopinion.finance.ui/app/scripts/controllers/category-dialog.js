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
 * @name greensopinionfinanceApp.controller:CategoryDialogCtrl
 * @description
 * # CategoryDialogCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('CategoryDialogCtrl',['$scope','$modalInstance','errorService','categoryService','transactionService','money','transaction', function ($scope,$modalInstance,errorService,categoryService,transactionService,money,transaction) {
    $scope.transaction = transaction;
    $scope.transactionAmount = money.format(Math.abs(transaction.amount));
    $scope.categoryName = transaction.categoryName;
    $scope.description = transaction.description;
    if ($scope.categoryName === undefined) {
      $scope.categoryName = '';
    }

    categoryService.list().then(function(result) {
      $scope.categoryList = result;
    });

    $scope.okMatching = function() {
      errorService.maintainErrorMessageInScope(
        categoryService.addRuleByName($scope.categoryName,{matchDescription:$scope.description}),
        $scope).then(function() {
          $modalInstance.close();
        });
    };
    $scope.okSingle = function() {
      errorService.maintainErrorMessageInScope(
        transactionService.putCategory($scope.transaction,$scope.categoryName),
        $scope).then(function() {
          $modalInstance.close();
        });
    };
    $scope.cancel = function() {
      $modalInstance.close();
    };
  }]);
