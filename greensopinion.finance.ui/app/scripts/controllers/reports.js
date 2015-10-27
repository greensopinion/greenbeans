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

    var createIncomeVersusExpenses = function(result) {
      $scope.incomeVersusExpenses = {
        title: result.title,
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

    var createExpensesByCategory = function (result) {
      $scope.expensesByCategory = {
        title: result.title,
        data: [ [], [], [], [] ],
        labels: [],
        series: [
          $scope.category[0],
          $scope.category[1],
          $scope.category[2],
          $scope.category[3]
        ],
        onClick: function(points) {
          var monthLabel = points[0].label;
          var monthIndex = $scope.incomeVersusExpenses.labels.indexOf(monthLabel);
          var month = $scope.incomeVersusExpenses.months[monthIndex];
          $scope.selectMonth(month);
        }
      };

      var index;
      for (index = Math.max(0,result.monthlyDetails.length-6);index < result.monthlyDetails.length;++index) {
        var monthDetails = result.monthlyDetails[index];

        $scope.expensesByCategory.labels.push(monthDetails.name);

        var amounts = [0,0,0,0];

        for (var index2 = 0;index2<monthDetails.categories.length ;++index2) {
          if (amounts[0] > 0 && amounts[1] > 0 && amounts[2] > 0 && amounts[3] > 0) {
            break;
          }
          var category = monthDetails.categories[index2];

          for (var i=0;i<4;++i) {
            if (amounts[i] === 0 && $scope.category[i] === category.name) {
              amounts[i] = Math.abs(category.amount / 100);
            }
          }
        }

        for (var j=0;j<4;++j) {
          $scope.expensesByCategory.data[j].push(amounts[j]);
        }
      }

    };
    $scope.switchReport = function (reportName) {
      $scope.reportName = reportName;
      if (reportName === 'IncomeVsExpenses') {
        $scope.title = $scope.incomeVersusExpenses.title;
      } else if (reportName === 'ExpensesByCategory') {
        $scope.title = $scope.expensesByCategory.title;
      }
    };
    $scope.showIncomeVersusExpenses = function() {
      return $scope.reportName === 'IncomeVsExpenses';
    };
    $scope.showExpensesByCategory = function() {
      return $scope.reportName === 'ExpensesByCategory';
    };
    $scope.category = ['','','',''];
    $scope.changeCategories = function() {
      createExpensesByCategory($scope.expensesByCategoryReport);
    };
    var selectDefaultCategories = function() {
      if ($scope.categoryList.length > 0) {
        var count = 0;
        for (var x = 0;x<$scope.categoryList.length && count < 4;++x) {
          $scope.category[count++] = $scope.categoryList[x].name;
        }
      }
    };
    var createCategoryList = function(report) {
      var categories = [];
      var categoryByName = {};
      var index;

      for (index = Math.max(0,report.monthlyDetails.length-6);index < report.monthlyDetails.length;++index) {
        var monthlyDetail = report.monthlyDetails[index];

        for (var i = 0;i<monthlyDetail.categories.length;++i) {
          var name = monthlyDetail.categories[i].name;
          var amount = monthlyDetail.categories[i].amount;

          var existing = categoryByName[name];
          if (existing) {
            existing.amount += amount;
          } else {
            var cat = {
              name: name,
              amount: amount
            };
            categoryByName[name] = cat;
            categories.push(cat);
          }
        }
      }

      categories.sort(function(a, b) {
          return a.amount - b.amount;
      });

      $scope.categoryList = categories;
    };

    $scope.selectDefaultCategories = function() {
      selectDefaultCategories();
      $scope.changeCategories();
    };

    reportService.incomeVersusExpenses().then(function(result) {
      createIncomeVersusExpenses(result);
      $scope.switchReport('IncomeVsExpenses');

      reportService.expensesByCategory().then(function(result) {
        createCategoryList(result);
        $scope.expensesByCategoryReport = result;
        selectDefaultCategories();
        createExpensesByCategory(result);
      });

    });

  }]);
