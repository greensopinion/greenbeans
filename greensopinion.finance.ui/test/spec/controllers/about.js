'use strict';

describe('Controller: AboutCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var AboutCtrl,
    scope, mockWindow, appServiceLocator,mockAboutService;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();

    mockAboutService = {
      getCopyrightNotice: function() {
        return 'test it yeah!';
      }
    };
    appServiceLocator = {
      getAboutService: function() {
          return mockAboutService;
      }
    };

    mockWindow = {
      appServiceLocator: appServiceLocator
    };

    AboutCtrl = $controller('AboutCtrl', {
      $scope: scope,
      $window: mockWindow
      // place here mocked dependencies
    });
  }));

  it('should have tests', function () {

  });
});
