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

describe('Service: eulaService', function() {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  var payload;

  beforeEach(module(function($provide) {
    $provide.factory('rest', function(MockRest) {
      payload = {
        userHasAgreedToLicense: false
      };
      return new MockRest(payload);
    });
  }));

  // instantiate service
  var eulaService, $rootScope;
  beforeEach(inject(function(_eulaService_, _$rootScope_) {
    $rootScope = _$rootScope_;
    eulaService = _eulaService_;
  }));

  it('should expose retrieveCurrentUserEulaStatus()', function() {
    expect(eulaService.retrieveCurrentUserEulaStatus).toBeDefined();
    var response;
    eulaService.retrieveCurrentUserEulaStatus().then(function(result) {
      response = result;
    });
    $rootScope.$digest();

    expect(response.method).toEqual('GET');
    expect(response.path).toEqual('/eula/user-agreements/current');
  });
  it('should expose putCurrentUserEulaStatus()', function() {
    expect(eulaService.putCurrentUserEulaStatus).toBeDefined();
    var response;
    eulaService.putCurrentUserEulaStatus().then(function(result) {
      response = result;
    });
    $rootScope.$digest();

    expect(response.method).toEqual('PUT');
    expect(response.path).toEqual('/eula/user-agreements/current');
  });

  it('should expose retrieveEula()', function() {
    expect(eulaService.retrieveEula).toBeDefined();
    var response;
    eulaService.retrieveEula().then(function(result) {
      response = result;
    });
    $rootScope.$digest();

    expect(response.method).toEqual('GET');
    expect(response.path).toEqual('/eula/current');
  });

  it('should expose eulaCheckComplete', function() {
    expect(eulaService.isEulaCheckComplete()).toBe(false);
    eulaService.setEulaCheckComplete();
    expect(eulaService.isEulaCheckComplete()).toBe(true);
  });
});
