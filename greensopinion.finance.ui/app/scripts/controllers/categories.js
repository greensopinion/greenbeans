'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:CategoriesCtrl
 * @description
 * # CategoriesCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('CategoriesCtrl',['$scope','errorService','categoryService', function ($scope,errorService,categoryService) {
    $scope.sortType = 'name';
    $scope.sortReverse = false;
    $scope.newCategoryName = '';

    $scope.addCategory = function() {
      return errorService.maintainErrorMessageInScope(categoryService.create($scope.newCategoryName),$scope)
        .then(function() {
          $scope.newCategoryName = '';
          return categoryService.list();
        }).then(function(result) {
          $scope.categoryList = result;
        });
    };

    categoryService.list().then(function(result) {
      $scope.categoryList = result;
    });
  }]);
