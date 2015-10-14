'use strict';

describe('Controller: MonthDetailCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var MonthDetailCtrl,
    scope,$rootScope, monthDetails, mockReportService;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller,  $q, _$rootScope_) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();


    monthDetails = {
      name: 'August 2015',
      categories: [
        {
          name: 'Groceries', amount: -123456
        },
        {
          name: 'Dog', amount: -7890
        }
      ]
    };
    mockReportService = {
      detailsForMonth: function() {
        return $q(function(resolve) {
          resolve(monthDetails);
        });
      }
    };

    MonthDetailCtrl = $controller('MonthDetailCtrl', {
      $scope: scope,
      reportService: mockReportService
    });
  }));

  it('should expose monthDetails', function () {
    $rootScope.$digest();
    expect(scope.monthDetails).toBe(monthDetails);
  });
  it('should expose expense data', function () {
    $rootScope.$digest();
    expect(scope.expenseCategories).toEqual(monthDetails.categories);
    expect(scope.monthExpenses).toBeDefined();
  });
  it('should expose formatAmount', function () {
    expect(scope.formatAmount).toBeDefined();
    expect(scope.formatAmount({amount: -12340})).toEqual('123.40');
  });
});
