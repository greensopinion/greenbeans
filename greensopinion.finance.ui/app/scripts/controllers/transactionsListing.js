'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:TransactionslistingCtrl
 * @description
 * # TransactionslistingCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('TransactionsListingCtrl',['$scope','$modalInstance','reportService','money','month', function ($scope,$modalInstance,reportService,money,month) {

    $scope.title = 'Transactions: '+ month.name;

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
  }]);
