/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

describe('Service: ErrorModel', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  it('Should accept message',inject(function (ErrorModel) {
    var error1 = new ErrorModel('message 1');
    var error2 = new ErrorModel('message 2');
    expect(error1).toBeDefined();
    expect(error1.message).toBe('message 1');
    expect(error2).toBeDefined();
    expect(error2.message).toBe('message 2');
  }));
});
