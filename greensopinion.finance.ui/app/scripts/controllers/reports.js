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
  .controller('ReportsCtrl',['$scope','$location','reportService', function ($scope,$location,reportService) {

    $scope.selectMonth = function(month) {
      $location.path('/month-detail/'+month.id);
    };

    var createChart = function(result) {
      $scope.incomeVersusExpenses = {
        months: [ ],
        data: [ [], [] ],
        labels: [],
        series: [ 'Income', 'Expenses' ],
        onClick: function(points) {
          var monthLabel = points[0].label;
          var monthIndex = $scope.incomeVersusExpenses.labels.indexOf(monthLabel);
          var month = $scope.incomeVersusExpenses.months[monthIndex];
          $scope.selectMonth(month);
        }
      };
      var index;
      for (index = Math.max(0,result.months.length-6);index < result.months.length;++index) {
        var month = result.months[index];
        $scope.incomeVersusExpenses.months.push(month);
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
