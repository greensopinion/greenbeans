/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

describe('Service: initializationService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var initializationService;
  beforeEach(inject(function (_initializationService_) {
    initializationService = _initializationService_;
  }));

  it('should expose isInitialized()', function () {
    expect(initializationService.isInitialized()).toBe(false);
    initializationService.initialized(true);
    expect(initializationService.isInitialized()).toBe(true);
    initializationService.initialized(false);
    expect(initializationService.isInitialized()).toBe(false);
  });
});
