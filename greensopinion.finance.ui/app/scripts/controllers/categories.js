/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:CategoriesCtrl
 * @description
 * # CategoriesCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('CategoriesCtrl',['$scope','errorService','categoryService','toastService', function ($scope,errorService,categoryService,toastService) {
    $scope.sortType = 'name';
    $scope.sortReverse = false;
    $scope.newCategoryName = '';

    $scope.addCategory = function() {
      return errorService.maintainErrorMessageInScope(categoryService.create($scope.newCategoryName),$scope)
        .then(function() {
          toastService.show('Category "'+$scope.newCategoryName+'" added.');
          $scope.newCategoryName = '';
          return categoryService.list();
        }).then(function(result) {
          $scope.categoryList = result;
        });
    };

    $scope.deleteCategory = function(category) {
      return errorService.maintainErrorMessageInScope(categoryService.delete(category.name),$scope)
        .then(function() {
          toastService.show('Category "'+category.name+'" deleted.');
          return categoryService.list();
        }).then(function(result) {
          $scope.categoryList = result;
        });
    };

    categoryService.list().then(function(result) {
      $scope.categoryList = result;
    });
  }]);
