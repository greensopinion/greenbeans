'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:CategoryDialogCtrl
 * @description
 * # CategoryDialogCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('CategoryDialogCtrl',['$scope','$modalInstance','errorService','categoryService','transactionService','transaction', function ($scope,$modalInstance,errorService,categoryService,transactionService,transaction) {
    $scope.transaction = transaction;
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
