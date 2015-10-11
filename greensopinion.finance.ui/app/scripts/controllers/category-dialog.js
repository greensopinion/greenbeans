'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:CategoryDialogCtrl
 * @description
 * # CategoryDialogCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('CategoryDialogCtrl',['$scope','$modalInstance','errorService','categoryService','transaction', function ($scope,$modalInstance,errorService,categoryService,transaction) {
    $scope.transaction = transaction;
    $scope.categoryName = transaction.categoryName;
    $scope.description = transaction.description;

    categoryService.list().then(function(result) {
      $scope.categoryList = result;
    });

    $scope.ok = function() {
      errorService.maintainErrorMessageInScope(
        categoryService.addRuleByName($scope.categoryName,{matchDescription:$scope.description}),
        $scope).then(function() {
          $modalInstance.close();
        });
    };
    $scope.cancel = function() {
      $modalInstance.close();
    };
  }]);
