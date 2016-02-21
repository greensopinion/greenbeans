/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
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

  it('should expose selectFiles()', function () {
      expect(scope.selectFiles).toBeDefined();
      payload.files = [ 'one', 'two'];
      scope.selectFiles();
      $rootScope.$digest();

      expect(payload.path).toBe('/imports/selected');
      expect(payload.method).toBe('GET');

      expect(scope.files).toBeDefined();
      expect(scope.files.length).toBe(2);
      expect(scope.files[0]).toBe('one');
      expect(scope.files[1]).toBe('two');
  });

  it('selectFiles() should handle errors',inject( function ($injector) {
    $injector.get('rest').setupError('error message');
    scope.selectFiles();
    $rootScope.$digest();

    expect(scope.errorMessage).toBe('error message');
  }));

  it('should expose hasFiles()',function() {
    expect(scope.hasFiles).toBeDefined();
    expect(scope.hasFiles()).toBe(false);
    scope.files = [];
    expect(scope.hasFiles()).toBe(false);
    scope.files = [ 'one' ];
    expect(scope.hasFiles()).toBe(true);
  });

  it('should expose cancel()',function() {
    expect(scope.cancel).toBeDefined();
    scope.files = [ 'one' ];
    scope.errorMessage = 'a message';
    scope.cancel();
    expect(scope.files).toBeUndefined();
    expect(scope.hasFiles()).toBe(false);
    expect(scope.errorMessage).toBeUndefined();
  });

  it('should expose importFiles()',function() {
    expect(scope.importFiles).toBeDefined();
    scope.files = [ 'one' ];
    scope.importFiles();
    $rootScope.$digest();
    expect(scope.files).toBeUndefined();
    expect(scope.hasFiles()).toBe(false);
  });
});
