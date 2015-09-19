'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:CategoriesCtrl
 * @description
 * # CategoriesCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('CategoriesCtrl',['$scope','categoryService', function ($scope,categoryService) {
    $scope.sortType = 'name';
    $scope.sortReverse = false;

    categoryService.list().then(function(result){
      $scope.categoryList = result;
    });

  }]);
