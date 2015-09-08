'use strict';

describe('Controller: MainCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var MainCtrl,
    scope, mockEncryptionSettingsService, encryptionSettings,$rootScope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, $q) {
    $rootScope = _$rootScope_;
    encryptionSettings = { initialized: false, configured: true };
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
        initializeMasterPassword: function () {
          return $q(function(resolve) {
            resolve({});
          });
        }
    };
    MainCtrl = $controller('MainCtrl', {
      $scope: scope,
      encryptionSettingsService: mockEncryptionSettingsService
    });
  }));

  it('should resolve encryption settings', function () {
    $rootScope.$digest();
    expect(scope.encryptionSettings).toBe(encryptionSettings);
  });

  it('exposes needsConfiguration()', function () {
    expect(scope.needsConfiguration).toBeDefined();
    expect(scope.needsConfiguration()).toBe(false);
    $rootScope.$digest();
    expect(scope.needsConfiguration()).toBe(false);

    encryptionSettings.configured = false;

    $rootScope.$digest();
    expect(scope.needsConfiguration()).toBe(true);
  });

  it('exposes needsInitialization()', function () {
    expect(scope.needsInitialization).toBeDefined();
    expect(scope.needsInitialization()).toBe(false);
    $rootScope.$digest();
    expect(scope.needsInitialization()).toBe(true);

    encryptionSettings.initialized = true;

    $rootScope.$digest();
    expect(scope.needsInitialization()).toBe(false);
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
    scope.configure();
    $rootScope.$digest();
    expect(scope.errorMessage).toBeUndefined();
  });

  it('exposes initialize()', function() {
    expect(scope.configure).toBeDefined();
    scope.initialize();
    $rootScope.$digest();
    expect(scope.errorMessage).toBe('You must enter a master password.');

    scope.formData.masterPassword = '1234';
    scope.initialize();
    $rootScope.$digest();
    expect(scope.errorMessage).toBeUndefined();
  });
});
