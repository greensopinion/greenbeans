'use strict';

describe('Service: reportService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var reportService, payload, $rootScope;

  beforeEach(module(function ($provide) {
    $provide.factory('rest', function(MockRest) {
      payload = {
        title: 'Report Title',
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
    expect(report.title).toBe('Report Title');
  });

  it('should provide expensesByCategory()', function () {
    var report;
    reportService.expensesByCategory().then(function(result) {
      report = result;
    });
    $rootScope.$digest();

    expect(report.path).toBe('/reports/expenses-by-category');
    expect(report.method).toBe('GET');
    expect(report.title).toBe('Report Title');
  });

  it('should provide transactionsForMonth()',function() {
    var report;
    reportService.transactionsForMonth(201509).then(function(result) {
      report = result;
    });
    $rootScope.$digest();

    expect(report.path).toBe('/reports/transactions/201509');
    expect(report.method).toBe('GET');
  });

  it('should provide detailsForMonth()',function() {
    var report;
    reportService.detailsForMonth(201509).then(function(result) {
      report = result;
    });
    $rootScope.$digest();

    expect(report.path).toBe('/reports/details/201509');
    expect(report.method).toBe('GET');
  });
});
