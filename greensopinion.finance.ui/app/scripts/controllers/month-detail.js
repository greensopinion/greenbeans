'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:MonthDetailCtrl
 * @description
 * # MonthDetailCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('MonthDetailCtrl',['$scope','$routeParams','reportService', function ($scope,$routeParams,reportService) {

      var monthId = $routeParams.month;
      $scope.title = 'Details for '+ monthId;

      reportService.transactionsForMonth(monthId).then(function(periodTransactions) {
        $scope.title = 'Details for '+ periodTransactions.name;
        $scope.periodTransactions = periodTransactions;
      });

  }]);
