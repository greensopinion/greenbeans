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

describe('Controller: CategoryDialogCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var $rootScope,CategoryDialogCtrl,
    scope,categoryList,
    mockCategoryService,
    mockTransactionService,
    modalClosed,
    addedCategoryRule,putCategoryValue;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_,$q) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
    modalClosed = false;

    categoryList = [
      {
        name: 'One'
      },
      {
        name: 'Two'
      }
    ];
    mockCategoryService = {
      list: function() {
        return $q(function(resolve) {
          resolve(categoryList);
        });
      },
      addRuleByName: function(categoryName,rule) {
        addedCategoryRule = {
          categoryName: categoryName,
          rule: rule
        };
        return $q(function(resolve) {
          resolve();
        });
      }
    };
    mockTransactionService = {
      putCategory: function(transaction,categoryName) {
        putCategoryValue = {
          transaction: transaction,
          categoryName: categoryName
        };
        return $q(function(resolve) {
          resolve();
        });
      }
    };

    CategoryDialogCtrl = $controller('CategoryDialogCtrl', {
      $scope: scope,
      $modalInstance: {
        close: function() {
          modalClosed = true;
        }
      },
      categoryService: mockCategoryService,
      transactionService: mockTransactionService,
      transaction: {
        id: 'id123',
        description: 'desc'
      }
    });
  }));

  it('should expose cancel() in scope', function () {
    expect(scope.cancel).toBeDefined();
    scope.cancel();
    expect(modalClosed).toBe(true);
  });

  it('should expose categoryList in scope', function () {
    $rootScope.$digest();
    expect(scope.categoryList).toBe(categoryList);
  });
  it('should expose okMatching() in scope', function () {
    expect(scope.okMatching).toBeDefined();
    scope.categoryName = 'Test Cat';

    scope.okMatching();
    $rootScope.$digest();

    expect(addedCategoryRule).toBeDefined();
    expect(addedCategoryRule.categoryName).toEqual('Test Cat');
    expect(addedCategoryRule.rule.matchDescription).toEqual('desc');

    expect(modalClosed).toBe(true);
  });
  it('should expose okSingle() in scope', function () {
    expect(scope.okSingle).toBeDefined();
    scope.categoryName = 'Test Cat';

    scope.okSingle();
    $rootScope.$digest();

    expect(putCategoryValue).toBeDefined();
    expect(putCategoryValue.transaction).toBe(scope.transaction);
    expect(putCategoryValue.categoryName).toEqual('Test Cat');

    expect(modalClosed).toBe(true);
  });
});
