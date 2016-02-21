/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

describe('Controller: EulaagreementCtrl', function() {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var EulaagreementCtrl,mockLocation,
    scope, $rootScope,eulaService, eulaCheckComplete,userHasAgreedToLicense,controller;

  // Initialize the controller and a mock scope
  beforeEach(inject(function($controller, _$rootScope_,$q,ErrorModel) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
    controller = $controller;

    mockLocation = {
      currentPath: undefined,
      path: function(newPath) {
        mockLocation.currentPath = newPath;
      }
    };

    eulaCheckComplete = false;
    eulaService = {
      isEulaCheckComplete: function() {
        return eulaCheckComplete;
      },
      setEulaCheckComplete: function() {
        eulaCheckComplete = true;
      },
      retrieveCurrentUserEulaStatus: function() {
        return $q(function(resolve) {
          var payload = { userHasAgreedToLicense: false };
          if (userHasAgreedToLicense) {
            payload = { userHasAgreedToLicense: true };
          }
          resolve(payload);
        });
      },
      putCurrentUserEulaStatus: function(agree) {
        return $q(function(resolve,reject) {
          if (agree) {
            resolve();
          } else {
            reject(new ErrorModel('you must agree'));
          }
        });
      },
      retrieveEula: function() {
        return $q(function(resolve) {
          resolve({text:'a license'});
        });
      }
    };
  }));
  var createController = function() {
    EulaagreementCtrl = controller('EulaAgreementCtrl', {
      $scope: scope,
      $location: mockLocation,
      eulaService: eulaService
    });
  };

  it('expect redirect if user has agreed to license', function() {
    userHasAgreedToLicense = true;
    createController();
    $rootScope.$digest();

    expect(mockLocation.currentPath).toBe('/encryption');
    expect(eulaCheckComplete).toBe(true);
  });

  it('expect eula to be defined if user not has agreed to license', function() {
    userHasAgreedToLicense = false;
    createController();
    $rootScope.$digest();
    expect(eulaCheckComplete).toBe(false);
    expect(scope.eula).toBeDefined();
  });

  it('expect agree() to be in scope', function() {
    userHasAgreedToLicense = false;
    createController();
    $rootScope.$digest();

    expect(scope.agree).toBeDefined();

    scope.agree();
    $rootScope.$digest();

    expect(mockLocation.currentPath).toBe('/encryption');
    expect(eulaCheckComplete).toBe(true);
  });

  it('expect disagree() to be in scope', function() {
    userHasAgreedToLicense = false;
    createController();
    $rootScope.$digest();

    expect(scope.disagree).toBeDefined();

    scope.disagree();
    $rootScope.$digest();

    expect(scope.errorMessage).toBe('you must agree');
  });
});
