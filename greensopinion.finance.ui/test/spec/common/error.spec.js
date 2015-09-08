'use strict';

describe('Service: Error', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  it('Should accept message', function () {
    var error1 = new Error('message 1');
    var error2 = new Error('message 2');
    expect(error1).toBeDefined();
    expect(error1.message).toBe('message 1');
    expect(error2).toBeDefined();
    expect(error2.message).toBe('message 2');
  });
});
