'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:TransactionslistingCtrl
 * @description
 * # TransactionslistingCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('TransactionsListingCtrl',['$scope','$routeParams', '$modal','reportService','money', function ($scope,$routeParams,$modal,reportService,money) {

    var monthId = $routeParams.month;

    $scope.title = 'Transactions: '+ monthId;

    var reloadTransactions = function() {
      reportService.transactionsForMonth(monthId).then(function(periodTransactions) {
        $scope.title = 'Transactions: '+ periodTransactions.name;
        $scope.periodTransactions = periodTransactions;
      });
    };
    reloadTransactions();

    $scope.incomeOf = function(transaction) {
      return transaction.amount > 0? money.format(transaction.amount) : '';
    };
    $scope.expenseOf = function(transaction) {
      return transaction.amount < 0? money.format(Math.abs(transaction.amount)) : '';
    };
    $scope.dateOf = function(transaction) {
      return transaction.date.slice(0,10);
    };
    $scope.sortExpense = function (transaction) {
      return -transaction.amount;
    };
    $scope.sortCategory = function (transaction) {
      return transaction.categoryName;
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
    $scope.editTransaction = function(transaction,enabled) {
      for (var x = 0;x<$scope.periodTransactions.transactions.length;++x) {
        $scope.periodTransactions.transactions[x].edit = false;
      }
      transaction.edit = enabled;
    };
    $scope.setCategory = function(transaction) {
      var modalInstance = $modal.open({
        animation: false,
        templateUrl: 'views/category-dialog.html',
        controller: 'CategoryDialogCtrl',
        resolve: {
          transaction: function () {
            return transaction;
          }
        }
      });
      modalInstance.result.then(function(){
        reloadTransactions();
      },function() {
        reloadTransactions();
      });
    };
  }]);
