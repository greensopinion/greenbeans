'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:TransactionslistingCtrl
 * @description
 * # TransactionslistingCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('TransactionsListingCtrl',['$scope','$modalInstance','reportService','categoryService','money','month', function ($scope,$modalInstance,reportService,categoryService,money,month) {

    $scope.title = 'Transactions: '+ month.name;

    categoryService.list().then(function(result) {
      $scope.categoryList = result;
    });
    reportService.transactionsForMonth(month.id).then(function(periodTransactions) {
      $scope.periodTransactions = periodTransactions;
    });
    
    $scope.incomeOf = function(transaction) {
      return transaction.amount > 0? money.format(transaction.amount) : '';
    };
    $scope.expenseOf = function(transaction) {
      return transaction.amount < 0? money.format(Math.abs(transaction.amount)) : '';
    };
    $scope.dateOf = function(transaction) {
      return transaction.date.slice(0,10);
    };
    $scope.ok = function() {
      $modalInstance.close();
    };
    $scope.sortExpense = function (transaction) {
      var sortable = -transaction.amount;
      console.log(''+sortable);
      return sortable;
    };
    $scope.sortIncome = function (transaction) {
      return transaction.amount;
    };

    $scope.sortType = $scope.dateOf;
    $scope.sortReverse = false;

    $scope.switchSortType = function(target) {
      var previousSortType = $scope.sortType;
      $scope.sortType = target;
      if (previousSortType !== $scope.sortType) {
        if ($scope.sortType === $scope.sortExpense || $scope.sortType === $scope.sortIncome) {
          $scope.sortReverse = true;
        } else {
          $scope.sortReverse = false;
        }
      } else {
        $scope.sortReverse = !$scope.sortReverse;
      }
    };
  }]);
