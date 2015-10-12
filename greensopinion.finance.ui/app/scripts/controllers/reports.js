'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:ReportsCtrl
 * @description
 * # ReportsCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .config(function (ChartJsProvider) {
    // Configure all charts
    ChartJsProvider.setOptions({
      colours: [  '#16a085','#e74c3c','#97BBCD', '#DCDCDC', '#FDB45C', '#949FB1', '#4D5360'],
      responsive: true
    });
  });

angular.module('greensopinionfinanceApp')
  .controller('ReportsCtrl',['$scope','reportService', function ($scope,reportService) {

    var createChart = function(result) {
      $scope.incomeVersusExpenses = {
        data: [ [], [] ],
        labels: [],
        series: [ 'Income', 'Expenses' ]
      };
      var index;
      for (index = Math.max(0,result.months.length-6);index < result.months.length;++index) {
        var month = result.months[index];
        $scope.incomeVersusExpenses.labels.push(month.name);
        $scope.incomeVersusExpenses.data[0].push(month.incomeTotal / 100);
        $scope.incomeVersusExpenses.data[1].push(month.expensesTotal / 100);
      }
    };

    reportService.incomeVersusExpenses().then(function(result) {
      $scope.report = result;
      createChart(result);
    });
  }]);
