/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
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
