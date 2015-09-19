'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:CategoriesCtrl
 * @description
 * # CategoriesCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('CategoriesCtrl',['$scope', function ($scope) {
    $scope.sortType = 'name';
    $scope.sortReverse = false;
    $scope.categoryList = [
      {
        name: 'Uncategorized',
        system: true
      },
			{ name: 'Bank Fees' },
			{ name: 'Books' },
			{ name: 'Business Expenses' },
			{ name: 'Charity' },
			{ name: 'Clothing' },
			{ name: 'Dog' },
			{ name: 'Dry Cleaning' },
			{ name: 'Entertainment' },
			{ name: 'Government (Returns)' },
			{ name: 'Groceries' },
			{ name: 'House' },
			{ name: 'Insurance' },
			{ name: 'Investments' },
			{ name: 'Medical' },
			{ name: 'Misc' },
			{ name: 'Mortgage' },
			{ name: 'Pay' },
			{ name: 'Payment/Transfer' },
			{ name: 'Personal' },
			{ name: 'Transport' },
			{ name: 'Sports' },
			{ name: 'Taxes' },
			{ name: 'Utilities'}
    ];
  }]);
