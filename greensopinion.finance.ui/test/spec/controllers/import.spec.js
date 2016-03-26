/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
