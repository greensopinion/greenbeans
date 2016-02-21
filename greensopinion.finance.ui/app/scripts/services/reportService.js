/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
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
      expensesByCategory: function() {
          return rest.get(API_BASE+'expenses-by-category');
      },
      transactionsForMonth: function(monthId) {
        return rest.get(API_BASE+'transactions/'+monthId);
      },
      detailsForMonth: function(monthId) {
        return rest.get(API_BASE+'details/'+monthId);
      }
    };

  }]);
