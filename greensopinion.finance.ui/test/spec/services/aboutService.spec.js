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

describe('Service: AboutService', function () {

    // load the controller's module
    beforeEach(module('greensopinionfinanceApp'));

    var aboutService, payload, $rootScope;


    beforeEach(module(function ($provide) {
      $provide.factory('rest', function(MockRest) {
        payload = { applicationName: 'App Name', copyrightNotice: 'Copyright Notice' };
        return new MockRest(payload);
      });
    }));

    beforeEach(inject(function ($injector,_$rootScope_) {
        $rootScope = _$rootScope_;
        aboutService = $injector.get('aboutService');
    }));

    it('about() should invoke web service',function() {
        var about;
        aboutService.about().then(function(result) {
          about = result;
        });
        $rootScope.$digest();

        expect(about.path).toBe('/abouts/current');
        expect(about.method).toBe('GET');
        expect(about.applicationName).toBe('App Name');
        expect(about.copyrightNotice).toBe('Copyright Notice');
    });
});
