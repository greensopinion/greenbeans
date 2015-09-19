'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:ReportsCtrl
 * @description
 * # ReportsCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('ReportsCtrl',[ '$scope','reportService','money', function ($scope,reportService,money) {

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
  }]);
