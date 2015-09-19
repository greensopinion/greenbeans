'use strict';

describe('Service: reportService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var reportService, payload, $rootScope;

  beforeEach(module(function ($provide) {
    $provide.factory('rest', function(MockRest) {
      payload = {
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
      return new MockRest(payload);
    });
  }));
  beforeEach(inject(function (_reportService_,_$rootScope_) {
    reportService = _reportService_;
    $rootScope = _$rootScope_;
  }));

  it('should provide incomeVersusExpenses()', function () {
    var report;
    reportService.incomeVersusExpenses().then(function(result) {
      report = result;
    });
    $rootScope.$digest();

    expect(report.path).toBe('/reports/income-vs-expenses');
    expect(report.method).toBe('GET');
    expect(report.title).toBe('Monthly Income vs Expenses');
  });

});
