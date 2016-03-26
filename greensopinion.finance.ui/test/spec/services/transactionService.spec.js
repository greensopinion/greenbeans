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

describe('Service: transactionService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var $rootScope,transactionService,payload;

  beforeEach(module(function ($provide) {
    $provide.factory('rest', function(MockRest) {
      payload = {};
      return new MockRest(payload);
    });
  }));

  beforeEach(inject(function (_$rootScope_,_transactionService_) {
    $rootScope = _$rootScope_;
    transactionService = _transactionService_;
  }));

  it('should expose putCategory()', function () {
    expect(transactionService.putCategory).toBeDefined();
    var result;
    transactionService.putCategory({id:'123'},'a name').then(function (value) {
      result = value;
    });
    $rootScope.$digest();

    expect(result.path).toBe('/transactions/123/category');
    expect(result.method).toBe('PUT');
    expect(result.entity.name).toBe('a name');
  });

});
