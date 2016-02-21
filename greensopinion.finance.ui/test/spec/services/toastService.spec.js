/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

describe('Service: toastService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var toastService, timerCallback,timerDelay;

  beforeEach(module(function ($provide) {
    $provide.factory('$timeout', function() {
      return function(callback,delay) {
        timerCallback = callback;
        timerDelay = delay;
      };
    });
  }));
  beforeEach(inject(function (_toastService_) {
    toastService = _toastService_;
  }));

  it('should expose show() and message()', function () {
    expect(toastService.show).toBeDefined();
    expect(toastService.message).toBeDefined();
    expect(toastService.clearMessage).toBeDefined();
    expect(toastService.message()).toBe('');

    toastService.show('some text');
    expect(toastService.message()).toBe('some text');

    expect(timerCallback).toBeDefined();
    expect(timerDelay).toBe(3500);
    timerCallback();

    expect(toastService.message()).toBe('');
  });

});
