'use strict';

/**
 * @ngdoc service
 * @name greensopinionfinanceApp.reportService
 * @description
 * # reportService
 * Service in the greensopinionfinanceApp.
 */
angular.module('greensopinionfinanceApp')
  .service('reportService',['$q','rest', function ($q,rest) {
    var API_BASE = '/reports/';
    return {
      incomeVersusExpenses: function() {
          return rest.get(API_BASE+'income-vs-expenses');
      },
      transactionsForMonth: function(monthId) {
        return rest.get(API_BASE+'transactions/'+monthId);
      },
      detailsForMonth: function(monthId) {
        return rest.get(API_BASE+'details/'+monthId);
      }
    };

  }]);
