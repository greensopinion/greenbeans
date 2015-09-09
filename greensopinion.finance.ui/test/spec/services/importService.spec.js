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

  it('importFiles() should invoke web service', function () {
    importService.importFiles();
    $rootScope.$digest();
    expect(payload.method).toBe('GET');
    expect(payload.path).toBe('/imports/new');
  });

});
