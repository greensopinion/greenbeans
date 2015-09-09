'use strict';

describe('Controller: ImportCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var ImportCtrl, scope, $rootScope,payload;


  beforeEach(module(function ($provide) {
    $provide.factory('rest', function(MockRest) {
      payload = {};
      return new MockRest(payload);
    });
  }));

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
    ImportCtrl = $controller('ImportCtrl', {
      $scope: scope
    });
  }));

  it('should expose importFiles()', function () {
      expect(scope.importFiles).toBeDefined();

      scope.importFiles();
      $rootScope.$digest();

      expect(payload.path).toBe('/imports/new');
      expect(payload.method).toBe('GET');
  });

  it('importFiles() should handle errors',inject( function ($injector) {
    $injector.get('rest').setupError('error message');
    scope.importFiles();
    $rootScope.$digest();

    expect(scope.errorMessage).toBe('error message');
  }));
});
