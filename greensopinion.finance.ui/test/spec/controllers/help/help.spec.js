/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

describe('Controller: HelpCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var HelpCtrl,
    scope,$rootScope,mockAboutService;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_,$q,_$location_,_$anchorScroll_) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();

    mockAboutService  = {
      about: function() {
        return $q(function(resolve){
          resolve({
            copyrightNotice: 'Copyright (c) 2015',
            applicationName: 'App Name'
          });
        });
      }
    };

    HelpCtrl = $controller('HelpCtrl', {
      $scope: scope,
      $location: _$location_,
      $anchorScroll: _$anchorScroll_,
      aboutService: mockAboutService
    });
  }));

  it('should be defined', function () {
    expect(HelpCtrl).toBeDefined();
  });

  it('should define applicationName in scope', function () {
      $rootScope.$digest();
      expect(scope.applicationName).toBe('App Name');
  });

  it('should expose scrollTo() in scope', function () {
      expect(scope.scrollTo).toBeDefined();
  });
});
