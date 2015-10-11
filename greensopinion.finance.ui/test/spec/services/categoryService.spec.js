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

  it('should expose create()', function () {
    expect(categoryService.create).toBeDefined();
    var result;
    categoryService.create('a name').then(function (value) {
      result = value;
    });
    $rootScope.$digest();

    expect(result.path).toBe('/categories');
    expect(result.method).toBe('POST');
    expect(result.entity.name).toBe('a name');
  });

  it('should expose delete()', function () {
    expect(categoryService.delete).toBeDefined();
    var result;
    categoryService.delete('a name/2').then(function (value) {
      result = value;
    });
    $rootScope.$digest();

    expect(result.path).toBe('/categories/a%20name%2F2');
    expect(result.method).toBe('DELETE');
  });
    it('should expose addRuleByName()', function () {
      expect(categoryService.addRuleByName).toBeDefined();
      var rule = { matchDescription: '123'};
      var result;
      categoryService.addRuleByName('a name/2',rule).then(function (value) {
        result = value;
      });
      $rootScope.$digest();

      expect(result.path).toBe('/categories/a%20name%2F2/rules');
      expect(result.method).toBe('POST');
      expect(result.entity).toBe(rule);
    });
});
