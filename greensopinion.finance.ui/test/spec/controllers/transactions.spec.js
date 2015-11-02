'use strict';

describe('Controller: TransactionsCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var TransactionsCtrl, mockReportService, incomeVersusExpensesReport,
    scope, $rootScope, openedPath;

  beforeEach(module(function ($provide) {
    $provide.factory('$location', function() {
      return {
        path: function(pathName) {
          openedPath = pathName;
        },
        hash: function() {
          return '';
        }
      };
    });
  }));

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_,_$location_, $q) {
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
    TransactionsCtrl = $controller('TransactionsCtrl', {
      $scope: scope,
      $location: _$location_,
      reportService: mockReportService
    });
  }));

  it('should expose reports', function () {
    $rootScope.$digest();
    expect(scope.report).toBe(incomeVersusExpensesReport);
    expect(scope.report.months[0].id).toEqual(201502);
    expect(scope.report.months[1].id).toEqual(201501);
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
    expect(openedPath).toBeDefined();
    expect(openedPath).toBe('/transactions-listing/201502');
  });
});
