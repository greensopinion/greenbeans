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

describe('Service: importService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  var payload,importService,$rootScope;

  beforeEach(module(function ($provide) {
    $provide.factory('rest', function(MockRest) {
      payload = {};
      return new MockRest(payload);
    });
  }));

  // instantiate service
  beforeEach(inject(function (_$rootScope_,$injector,_importService_) {
    $rootScope = _$rootScope_;
    importService = _importService_;
  }));

  it('selectedFiles() should invoke web service', function () {
    importService.selectedFiles();
    $rootScope.$digest();
    expect(payload.method).toBe('GET');
    expect(payload.path).toBe('/imports/selected');
  });

  it('importFiles() should invoke web service', function () {
    importService.importFiles(['one','two'],true);
    $rootScope.$digest();
    expect(payload.method).toBe('POST');
    expect(payload.path).toBe('/imports');
    expect(payload.entity).toEqual({files:['one','two'],deleteAfterImport:true});
  });
});
