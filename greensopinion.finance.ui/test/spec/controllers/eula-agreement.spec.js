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
