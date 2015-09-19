'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:ReportsCtrl
 * @description
 * # ReportsCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('ReportsCtrl',[ '$scope','$modal','reportService','money', function ($scope,$modal,reportService,money) {

    reportService.incomeVersusExpenses().then(function(result) {
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
      $modal.open({
        animation: true,
        templateUrl: 'views/transactions-listing.html',
        controller: 'TransactionsListingCtrl',
        size: 'transactions-listing',
        resolve: {
          month: function () {
            return month;
          }
        }
      });
    };
  }]);
