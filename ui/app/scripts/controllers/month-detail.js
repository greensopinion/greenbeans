/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:MonthDetailCtrl
 * @description
 * # MonthDetailCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('MonthDetailCtrl',['$scope','$routeParams','reportService', 'money', function ($scope,$routeParams,reportService,money) {

      var monthId = $routeParams.month;
      $scope.title = 'Details for '+ monthId;

      $scope.chartOptions = {
          animateRotate: false,
          animateScale: false
      };

      var createExpensesData = function (monthDetails) {
        $scope.expenseCategories = [];
        $scope.monthExpenses = {
          data: [],
          labels: []
        };
        var categoryCount = 0;
        var otherAmount = 0;
        var index;
        for (index = 0;index < monthDetails.categories.length;++index) {
          var categorySummary = monthDetails.categories[index];
          if (categorySummary.amount > 0) {
            continue;
          }
          $scope.expenseCategories.push(categorySummary);

          ++categoryCount;
          var amount = Math.abs(categorySummary.amount) / 100;
          if (categoryCount <= 6) {
            $scope.monthExpenses.labels.push(categorySummary.name);
            $scope.monthExpenses.data.push(amount);
          } else {
            otherAmount += amount;
          }
        }
        if (otherAmount > 0) {
          otherAmount = Math.round(otherAmount * 100) / 100;
          $scope.monthExpenses.labels.push('Other');
          $scope.monthExpenses.data.push(otherAmount);
        }
      };
      $scope.formatAmount = function(categorySummary) {
        return money.format(Math.abs(categorySummary.amount));
      };
      reportService.detailsForMonth(monthId).then(function(monthDetails) {
        $scope.title = 'Details for '+ monthDetails.name;
        $scope.monthDetails = monthDetails;
        createExpensesData(monthDetails);
      });
  }]);
