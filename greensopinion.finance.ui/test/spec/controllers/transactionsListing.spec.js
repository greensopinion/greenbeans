'use strict';

describe('Controller: TransactionsListingCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var TransactionsListingCtrl,
    scope,$rootScope,mockReportService, periodTransactions;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, $q) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
    periodTransactions = {
      name: 'August 2015',
      transactions: [
        {
          date: '2015-02-28T23:41:00.023Z', description: 'a purchase', amount: -123456
        }
      ]
    };
    mockReportService = {
      transactionsForMonth: function() {
        return $q(function(resolve) {
          resolve( periodTransactions);
        });
      }
    };
    TransactionsListingCtrl = $controller('TransactionsListingCtrl', {
      $scope: scope,
      $modalInstance: {},
      reportService: mockReportService,
      month: {
        name: 'August 2015',
        id: 201508
      }
    });
  }));

  it('should expose title in scope', function () {
    expect(scope.title).toBe('Transactions: August 2015');
  });

  it('should expose incomeOf() in scope',function() {
    expect(scope.incomeOf).toBeDefined();
    expect(scope.incomeOf({amount:100023})).toBe('1000.23');
    expect(scope.incomeOf({amount:-100023})).toBe('');
  });

  it('should expose expenseOf() in scope',function() {
    expect(scope.expenseOf).toBeDefined();
    expect(scope.expenseOf({amount:100023})).toBe('');
    expect(scope.expenseOf({amount:-100023})).toBe('1000.23');
  });

  it('should expose dateOf() in scope',function() {
    expect(scope.dateOf).toBeDefined();
    expect(scope.dateOf({date:'2015-02-28T23:41:00.023Z'})).toBe('2015-02-28');
  });

  it('should expose periodTransactions in scope',function() {
    $rootScope.$digest();
    expect(scope.periodTransactions).toBe(periodTransactions);
  });
});
