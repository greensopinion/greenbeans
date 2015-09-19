'use strict';

describe('Controller: ReportsCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var ReportsCtrl, mockReportService, incomeVersusExpensesReport,
    scope, $rootScope, openedDialog;

  beforeEach(module(function ($provide) {
    $provide.factory('$modal', function() {
      return {
        open: function(dialogDetails) {
          openedDialog = dialogDetails;
        }
      };
    });
  }));

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_,_$modal_, $q) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
    incomeVersusExpensesReport = {
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
    };
    mockReportService = {
      incomeVersusExpenses:  function() {
          return $q(function(resolve) {
            resolve(incomeVersusExpensesReport);
          });
      }
    };
    ReportsCtrl = $controller('ReportsCtrl', {
      $scope: scope,
      $modal: _$modal_,
      reportService: mockReportService
    });
  }));

  it('should expose reports', function () {
    $rootScope.$digest();
    expect(scope.report).toBe(incomeVersusExpensesReport);
  });

  it('should expose formatCurrency()',function() {
    expect(scope.formatCurrency).toBeDefined();
    expect(scope.formatCurrency(13020)).toBe('130.20');
  });
  it('should expose classOfAmount()',function() {
    expect(scope.classOfAmount).toBeDefined();
    expect(scope.classOfAmount(10)).toBe('');
    expect(scope.classOfAmount(-10)).toBe('text-danger');
  });
  it('should expose openTransactions()',function() {
    var month = {
      id: 201502,
      name: '2015-02',
      incomeTotal: 1000100,
      expensesTotal: 543202
    };
    expect(scope.openTransactions).toBeDefined();
    scope.openTransactions(month);
    expect(openedDialog).toBeDefined();
    expect(openedDialog.resolve.month).toBeDefined();
    var result = openedDialog.resolve.month();
    expect(result).toBe(month);
  });
});
