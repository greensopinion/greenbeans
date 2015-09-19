'use strict';

describe('Controller: ReportsCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var ReportsCtrl, mockReportService, incomeVersusExpensesReport,
    scope, $rootScope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, $q) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
    incomeVersusExpensesReport = {
      title: 'Monthly Income vs Expenses',
      months: [
        {
          name: '2015-01',
          incomeTotal: 1000000,
          expensesTotal: 543201
        },
        {
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
});
