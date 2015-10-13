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

      var createExpensesChart = function (monthDetails) {
        $scope.monthExpenses = {
          data: [],
          labels: []
        };
        var index;
        for (index = 0;index < monthDetails.categories.length;++index) {
          var categorySummary = monthDetails.categories[index];
          if (categorySummary.amount > 0) {
            continue;
          }

          $scope.monthExpenses.labels.push(categorySummary.name);
          $scope.monthExpenses.data.push(Math.abs(categorySummary.amount) / 100);
        }
      };

      reportService.detailsForMonth(monthId).then(function(monthDetails) {
        $scope.title = 'Details for '+ monthDetails.name;
        $scope.monthDetails = monthDetails;
        createExpensesChart(monthDetails);
      });

  }]);
