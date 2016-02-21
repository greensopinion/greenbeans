/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
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
