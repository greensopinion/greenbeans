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
