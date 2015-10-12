'use strict';

describe('Controller: MonthDetailCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var MonthDetailCtrl,
    scope,$rootScope, periodTransactions, mockReportService;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller,  $q, _$rootScope_) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();


    periodTransactions = {
      name: 'August 2015',
      transactions: [
        {
          date: '2015-02-28T23:41:00.023Z', description: 'a purchase', amount: -123456
        },
        {
          date: '2015-02-30T23:41:00.023Z', description: 'a purchase2', amount: -140456
        },
        {
          date: '2015-02-30T23:41:00.023Z', description: 'deposit1', amount: 10243
        },
        {
          date: '2015-02-31T23:41:00.023Z', description: 'deposit2', amount: 2043
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

    MonthDetailCtrl = $controller('MonthDetailCtrl', {
      $scope: scope,
      reportService: mockReportService
    });
  }));

  it('should expose periodTransactions', function () {
    $rootScope.$digest();
    expect(scope.periodTransactions).toBe(periodTransactions);
  });
});
