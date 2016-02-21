/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

describe('Controller: ReportsCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var ReportsCtrl,
    scope,$rootScope,mockReportService;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, $q) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();

    mockReportService = {
      incomeVersusExpenses:  function() {
          return $q(function(resolve) {
            resolve({
              title: 'Monthly Income vs Expenses',
              months: [
                {
                  id: 201501,
                  name: '2015-01',
                  incomeTotal: 1000000,
                  expensesTotal: 543201
                },
                {
                  id: 201502,
                  name: '2015-02',
                  incomeTotal: 1000100,
                  expensesTotal: 543202
                }
              ]
            });
          });
      },
      expensesByCategory: function() {
        return $q(function(resolve) {
          resolve({
            title: 'Monthly Expenses by Category',
            monthlyDetails: []
          });
        });
      }
    };
    ReportsCtrl = $controller('ReportsCtrl', {
      $scope: scope,
      reportService: mockReportService
    });
  }));

  it('should expose report', function () {
    $rootScope.$digest();

    expect(scope.incomeVersusExpenses).toBeDefined();
    expect(scope.incomeVersusExpenses.onClick).toBeDefined();

    expect(scope.expensesByCategory).toBeDefined();
    expect(scope.expensesByCategory.onClick).toBeDefined();

    expect(scope.title).toBe('Monthly Income vs Expenses');
  });

  it('should expose chart data', function () {
    $rootScope.$digest();
    expect(scope.incomeVersusExpenses).toBeDefined();
    expect(scope.incomeVersusExpenses.series).toEqual([ 'Income', 'Expenses' ]);
    expect(scope.incomeVersusExpenses.labels).toEqual([ '2015-01', '2015-02' ]);
    expect(scope.incomeVersusExpenses.data).toEqual([
      [ 10000.00, 10001.00 ],
      [ 5432.01, 5432.02 ]
    ]);
  });

  it('should expose switchReport()',function() {
    $rootScope.$digest();
    expect(scope.title).toBe('Monthly Income vs Expenses');
    expect(scope.showIncomeVersusExpenses()).toBe(true);
    expect(scope.showExpensesByCategory()).toBe(false);

    scope.switchReport('ExpensesByCategory');

    expect(scope.title).toBe('Monthly Expenses by Category');
    expect(scope.showIncomeVersusExpenses()).toBe(false);
    expect(scope.showExpensesByCategory()).toBe(true);
  });

  it ('should expose categoryList', function() {
    $rootScope.$digest();
    expect(scope.categoryList).toBeDefined();
  });
});
