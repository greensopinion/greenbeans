'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:ReportsCtrl
 * @description
 * # ReportsCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('ReportsCtrl',['$scope','reportService', function ($scope,reportService) {

    reportService.incomeVersusExpenses().then(function(result) {
      $scope.report = result;
    });
  }]);
