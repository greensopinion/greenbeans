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

describe('Directive: toast', function () {

  // load the directive's module
  beforeEach(module('greensopinionfinanceApp'));

  var element,$rootScope,toastService,
    scope;

  beforeEach(inject(function (_$rootScope_,_toastService_) {
    $rootScope = _$rootScope_;
    toastService = _toastService_;
    scope = $rootScope.$new();
  }));

  it('should define toastMessage() in scope', inject(function ($compile) {
    element = angular.element('<toast></toast>');
    element = $compile(element)(scope);
    $rootScope.$digest();
    expect(scope.toastMessage).toBeDefined();
  }));
  it('should show toast', inject(function ($compile) {
    element = angular.element('<toast></toast>');
    element = $compile(element)(scope);
    $rootScope.$digest();
    expect(element.text().trim()).toBe('');

    toastService.show('some info');

    $rootScope.$digest();

    expect(element.text().trim()).toBe('some info');
  }));
});
