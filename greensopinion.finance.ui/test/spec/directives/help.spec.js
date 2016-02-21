/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
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
