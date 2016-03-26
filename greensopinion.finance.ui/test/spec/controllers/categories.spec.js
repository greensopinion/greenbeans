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

describe('Controller: CategoriesCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var CategoriesCtrl,
    scope,$rootScope,mockCategoryService,categoryList,createdCategoryName;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, $q, _errorService_) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
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
      create: function(name) {
        createdCategoryName = name;
        categoryList.push({ name: name });
        return $q(function(resolve) {
          resolve();
        });
      },
      delete: function(name) {
        expect(name).toBe('One');
        categoryList = [ {name: 'Two'}];
        return $q(function(resolve) {
          resolve();
        });
      }
    };

    CategoriesCtrl = $controller('CategoriesCtrl', {
      $scope: scope,
      errorService: _errorService_,
      categoryService: mockCategoryService
    });
  }));

  it('should expose categories', function () {
    $rootScope.$digest();
    expect(scope.categoryList).toBe(categoryList);
  });

  it('should expose addCategory()', function () {
    expect(scope.addCategory).toBeDefined();
    scope.newCategoryName = 'a name';
    scope.addCategory();
    $rootScope.$digest();
    expect(createdCategoryName).toBe('a name');
    expect(scope.newCategoryName).toBe('');
    expect(scope.categoryList.length).toBe(3);
    expect(scope.categoryList[2].name).toBe('a name');
  });
    it('should expose deleteCategory()', function () {
      expect(scope.deleteCategory).toBeDefined();
      scope.deleteCategory({ name: 'One' });
      $rootScope.$digest();
      expect(scope.categoryList.length).toBe(1);
      expect(scope.categoryList[0].name).toBe('Two');
    });
});
