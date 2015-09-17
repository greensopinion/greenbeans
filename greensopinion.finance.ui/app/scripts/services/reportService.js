'use strict';

/**
 * @ngdoc service
 * @name greensopinionfinanceApp.reportService
 * @description
 * # reportService
 * Service in the greensopinionfinanceApp.
 */
angular.module('greensopinionfinanceApp')
  .service('reportService',['$q', function ($q) {
    var expensesVersusIncome = function() {
      return $q(function(resolve) {
        resolve( {
          title: 'Monthly Expenses vs Income',
          months: [
            {
              name: '2015-07',
              expensesTotal: '5854.00',
              incomeTotal: '10000.00'
            },
            {
              name: '2015-08',
              expensesTotal: '9854.00',
              incomeTotal: '10000.00'
            },
            {
              name: '2015-09',
              expensesTotal: '6854.00',
              incomeTotal: '10000.00'
            },
            {
              name: '2015-10',
              expensesTotal: '6553.21',
              incomeTotal: '10000.00'
            }
          ]
        });
      });
    };
    return {
      expensesVersusIncome: expensesVersusIncome
    };
  }]);
