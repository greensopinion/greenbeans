'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:TransactionsCtrl
 * @description
 * # TransactionsCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('TransactionsCtrl',[ '$scope','$location','reportService','money', function ($scope,$location,reportService,money) {

    reportService.incomeVersusExpenses().then(function(result) {
      result.months.reverse();
      $scope.report = result;
    });

    $scope.formatCurrency =  function(value) {
      return money.format(value);
    };
    $scope.classOfAmount = function(value) {
      if (value < 0) {
        return 'text-danger';
      }
      return '';
    };
    $scope.openTransactions = function(month) {
      $location.path('/transactions-listing/'+month.id);
    };
  }]);
