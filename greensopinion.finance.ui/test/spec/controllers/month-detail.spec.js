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
  it('should expose chartOptions in scope', function () {
    expect(scope.chartOptions).toBeDefined();
    expect(scope.chartOptions.animateRotate).toBe(false);
    expect(scope.chartOptions.animateScale).toBe(false);
  });
});
