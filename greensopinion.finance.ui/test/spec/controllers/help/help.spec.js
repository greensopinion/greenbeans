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
