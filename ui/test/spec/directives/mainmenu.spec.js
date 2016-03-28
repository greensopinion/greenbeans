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

describe('Directive: mainmenu', function() {

  // load the directive's module
  beforeEach(module('greensopinionfinanceApp'));

  var $rootScope, $location, initializationService, element,
    scope, eulaService;


  beforeEach(inject(function(_$rootScope_, _$location_, _initializationService_,_eulaService_) {
    $rootScope = _$rootScope_;
    $location = _$location_;
    eulaService = _eulaService_;
    initializationService = _initializationService_;

    initializationService.initialized(true);
    scope = $rootScope.$new();
  }));

  it('should expose getClass', inject(function($compile) {
    element = angular.element('<mainmenu></mainmenu>');
    element = $compile(element)(scope);
    $rootScope.$digest();

    expect(scope.getClass).toBeDefined();
    expect(scope.getClass('/not-here')).toBe('');

    $location.path('/');
    expect(scope.getClass('/')).toBe('active');
    expect(scope.getClass('/about')).toBe('');

    $location.path('/about');
    expect(scope.getClass('/')).toBe('');
    expect(scope.getClass('/about')).toBe('active');
  }));

  it('should expose isVisible()', inject(function($compile) {
    element = angular.element('<mainmenu></mainmenu>');
    element = $compile(element)(scope);
    $rootScope.$digest();

    expect(scope.isVisible({})).toBe(false);
    expect(scope.isVisible({
      insecure: true
    })).toBe(false);

    eulaService.setEulaCheckComplete();
    initializationService.initialized(false);

    expect(scope.isVisible({})).toBe(false);
    expect(scope.isVisible({
      insecure: false
    })).toBe(false);
    expect(scope.isVisible({
      insecure: true
    })).toBe(true);
    expect(scope.isVisible({
      insecure: true,
      path: '/'
    })).toBe(true);

    initializationService.initialized(true);

    expect(scope.isVisible({})).toBe(true);
    expect(scope.isVisible({
      insecure: false
    })).toBe(true);
    expect(scope.isVisible({
      insecure: true
    })).toBe(true);
    expect(scope.isVisible({
      insecure: true
    })).toBe(true);
    expect(scope.isVisible({
      insecure: true,
      path: '/encryption'
    })).toBe(false);
  }));

  it('should have menu elements', inject(function($compile) {
    element = angular.element('<mainmenu></mainmenu>');
    element = $compile(element)(scope);
    eulaService.setEulaCheckComplete();

    $rootScope.$digest();

    var homeNav = element.find('a:contains("Import")');
    expect(homeNav.attr('href')).toBe('#/import');

    var aboutNav = element.find('a:contains("About")');
    expect(aboutNav.attr('href')).toBe('#/about');
  }));

  it('should hide menu elements based on eula check', inject(function($compile) {
    element = angular.element('<mainmenu></mainmenu>');
    element = $compile(element)(scope);

    var aboutNav = element.find('a:contains("About")');
    expect(aboutNav.length).toBe(0);
  }));
});
