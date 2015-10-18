'use strict';

describe('Service: transactionService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var $rootScope,transactionService,payload;

  beforeEach(module(function ($provide) {
    $provide.factory('rest', function(MockRest) {
      payload = {};
      return new MockRest(payload);
    });
  }));

  beforeEach(inject(function (_$rootScope_,_transactionService_) {
    $rootScope = _$rootScope_;
    transactionService = _transactionService_;
  }));

  it('should expose putCategory()', function () {
    expect(transactionService.putCategory).toBeDefined();
    var result;
    transactionService.putCategory({id:'123'},'a name').then(function (value) {
      result = value;
    });
    $rootScope.$digest();

    expect(result.path).toBe('/transactions/123/category');
    expect(result.method).toBe('PUT');
    expect(result.entity.name).toBe('a name');
  });

});
