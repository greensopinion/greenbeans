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
});
