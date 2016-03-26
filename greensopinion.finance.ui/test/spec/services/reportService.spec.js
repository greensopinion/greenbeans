/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
