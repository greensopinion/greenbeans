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

describe('Directive: help', function () {

  // load the directive's module
  beforeEach(module('greensopinionfinanceApp'));

  var element,
    scope, $rootScope;

  beforeEach(inject(function (_$rootScope_) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
  }));

  it('should provide help', inject(function ($compile) {
    element = angular.element('<help target="atarget" text="Some Text"></help>');
    element = $compile(element)(scope);
    $rootScope.$digest();

    expect(scope.show).toBeDefined();
    expect(scope.hide).toBeDefined();
    expect(scope.showing).toBe(false);

    scope.show();
    $rootScope.$digest();

    expect(scope.showing).toBe(true);

    expect(element.text()).toContain('Some Text');

    scope.hide();
    $rootScope.$digest();

    expect(element.text()).not.toContain('Some Text');
  }));
});
