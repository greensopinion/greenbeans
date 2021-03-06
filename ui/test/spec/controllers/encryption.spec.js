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

describe('Controller: EncryptionCtrl', function() {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var EncryptionCtrl,
    scope, mockEncryptionSettingsService, encryptionSettings, $rootScope, initializationService;

  // Initialize the controller and a mock scope
  beforeEach(inject(function($controller, _$rootScope_, $q, _initializationService_) {
    $rootScope = _$rootScope_;
    initializationService = _initializationService_;
    encryptionSettings = {
      initialized: false,
      configured: true
    };
    scope = $rootScope.$new();
    mockEncryptionSettingsService = {
      get: function() {
        return $q(function(resolve) {
          resolve(encryptionSettings);
        });
      },
      configureMasterPassword: function() {
        return $q(function(resolve) {
          resolve({});
        });
      },
      initializeMasterPassword: function() {
        return $q(function(resolve) {
          resolve({});
        });
      },
      resetMasterPassword: function() {
        return $q(function(resolve) {
          resolve({});
        });
      }
    };
    EncryptionCtrl = $controller('EncryptionCtrl', {
      $scope: scope,
      encryptionSettingsService: mockEncryptionSettingsService,
      initializationService: initializationService
    });
  }));

  it('should resolve encryption settings', function() {
    $rootScope.$digest();
    expect(scope.encryptionSettings).toBe(encryptionSettings);
  });

  it('exposes needsConfiguration()', function() {
    expect(scope.needsConfiguration).toBeDefined();
    expect(scope.needsConfiguration()).toBe(false);
    $rootScope.$digest();
    expect(scope.needsConfiguration()).toBe(false);

    encryptionSettings.configured = false;

    $rootScope.$digest();
    expect(scope.needsConfiguration()).toBe(true);
  });

  it('exposes needsInitialization()', function() {
    expect(scope.needsInitialization).toBeDefined();
    expect(scope.needsInitialization()).toBe(false);
    $rootScope.$digest();
    expect(scope.needsInitialization()).toBe(true);

    encryptionSettings.initialized = true;

    $rootScope.$digest();
    expect(scope.needsInitialization()).toBe(false);

    encryptionSettings.initialized = false;
    encryptionSettings.configured = false;

    $rootScope.$digest();
    expect(scope.needsInitialization()).toBe(false);

    encryptionSettings.initialized = false;
    encryptionSettings.configured = true;

    $rootScope.$digest();
    expect(scope.needsInitialization()).toBe(true);
  });

  it('exposes formData', function() {
    expect(scope.formData).toBeDefined();
    expect(scope.formData.masterPassword).toBeDefined();
    expect(scope.formData.masterPassword2).toBeDefined();
  });

  it('exposes configure()', function() {
    expect(scope.configure).toBeDefined();
    scope.configure();
    $rootScope.$digest();
    expect(scope.errorMessage).toBe('You must enter a master password.');

    scope.formData.masterPassword = '1234';
    scope.configure();
    $rootScope.$digest();
    expect(scope.errorMessage).toBe('Passwords entered do not match.');

    scope.formData.masterPassword2 = '1234';
    expect(initializationService.isInitialized()).toBe(false);
    scope.configure();
    encryptionSettings.initialized = true;
    encryptionSettings.configured = true;
    $rootScope.$digest();
    expect(scope.errorMessage).toBeUndefined();
    expect(initializationService.isInitialized()).toBe(true);
  });

  it('exposes initialize()', function() {
    expect(scope.initialize).toBeDefined();
    scope.initialize();
    $rootScope.$digest();
    expect(scope.errorMessage).toBe('You must enter a master password.');

    scope.formData.masterPassword = '1234';
    expect(initializationService.isInitialized()).toBe(false);
    scope.initialize();
    encryptionSettings.initialized = true;
    encryptionSettings.configured = true;
    $rootScope.$digest();
    expect(scope.errorMessage).toBeUndefined();
    expect(initializationService.isInitialized()).toBe(true);
  });

  it('exposes resetMasterPassword()', function() {
    expect(scope.resetMasterPassword).toBeDefined();
    scope.resetMasterPassword();
    $rootScope.$digest();
    expect(scope.errorMessage).toBe('You must enter the original master password.');

    scope.formData.masterPassword = '1234';
    expect(initializationService.isInitialized()).toBe(false);
    scope.resetMasterPassword();
    $rootScope.$digest();

    expect(scope.errorMessage).toBe('You must enter a new master password.');

    scope.formData.newMasterPassword = '5678';
    expect(initializationService.isInitialized()).toBe(false);
    scope.resetMasterPassword();
    $rootScope.$digest();

    expect(scope.errorMessage).toBe('New master passwords entered do not match.');

    scope.formData.newMasterPassword2 = '5678';
    scope.resetMasterPassword();
    encryptionSettings.initialized = true;
    encryptionSettings.configured = true;
    $rootScope.$digest();

    expect(scope.errorMessage).toBeUndefined();
    expect(initializationService.isInitialized()).toBe(true);
  });
});
