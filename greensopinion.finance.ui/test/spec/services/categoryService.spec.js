'use strict';

describe('Service: categoryService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var categoryService, payload, $rootScope;

  beforeEach(module(function ($provide) {
    $provide.factory('rest', function(MockRest) {
      payload = {};
      return new MockRest(payload);
    });
  }));

  beforeEach(inject(function (_categoryService_,_$rootScope_) {
    $rootScope = _$rootScope_;
    categoryService = _categoryService_;
  }));

  it('should expose list()', function () {
    expect(categoryService.list).toBeDefined();
    var result;
    categoryService.list().then(function (value) {
      result = value;
    });
    $rootScope.$digest();
    
    expect(result.path).toBe('/categories');
    expect(result.method).toBe('GET');
  });

});
