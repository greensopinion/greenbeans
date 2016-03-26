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

describe('Service: categoryService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var categoryService, payload, $rootScope;

  beforeEach(module(function ($provide) {
    $provide.factory('rest', function(MockRest) {
      payload = {};
      return new MockRest(payload);
    });
  }));

  beforeEach(inject(function (_categoryService_,_$rootScope_) {
    $rootScope = _$rootScope_;
    categoryService = _categoryService_;
  }));

  it('should expose list()', function () {
    expect(categoryService.list).toBeDefined();
    var result;
    categoryService.list().then(function (value) {
      result = value;
    });
    $rootScope.$digest();

    expect(result.path).toBe('/categories');
    expect(result.method).toBe('GET');
  });

  it('should expose create()', function () {
    expect(categoryService.create).toBeDefined();
    var result;
    categoryService.create('a name').then(function (value) {
      result = value;
    });
    $rootScope.$digest();

    expect(result.path).toBe('/categories');
    expect(result.method).toBe('POST');
    expect(result.entity.name).toBe('a name');
  });

  it('should expose delete()', function () {
    expect(categoryService.delete).toBeDefined();
    var result;
    categoryService.delete('a name/2').then(function (value) {
      result = value;
    });
    $rootScope.$digest();

    expect(result.path).toBe('/categories/a%20name%2F2');
    expect(result.method).toBe('DELETE');
  });
    it('should expose addRuleByName()', function () {
      expect(categoryService.addRuleByName).toBeDefined();
      var rule = { matchDescription: '123'};
      var result;
      categoryService.addRuleByName('a name/2',rule).then(function (value) {
        result = value;
      });
      $rootScope.$digest();

      expect(result.path).toBe('/categories/a%20name%2F2/rules');
      expect(result.method).toBe('POST');
      expect(result.entity).toBe(rule);
    });
});
