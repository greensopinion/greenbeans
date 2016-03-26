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
