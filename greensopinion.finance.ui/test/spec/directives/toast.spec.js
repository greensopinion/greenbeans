/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
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
