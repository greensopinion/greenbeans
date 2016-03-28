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

describe('Service: EncryptionSettingsService', function () {

    // load the controller's module
    beforeEach(module('greensopinionfinanceApp'));

    var encryptionSettingsService, payload, $rootScope;

    beforeEach(module(function ($provide) {
      $provide.factory('rest', function(MockRest) {
        payload = { configured: false, initialized: false };
        return new MockRest(payload);
      });
    }));
    beforeEach(inject(function ($injector,_$rootScope_) {
        $rootScope = _$rootScope_;
        encryptionSettingsService = $injector.get('encryptionSettingsService');
    }));

    it('get should invoke web service',function() {
        var settings;
        encryptionSettingsService.get().then(function(result) {
          settings = result;
        });
        $rootScope.$digest();

        expect(settings.path).toBe('/encryption-settings/current');
        expect(settings.method).toBe('GET');
        expect(settings.initialized).toBe(false);
        expect(settings.configured).toBe(false);
    });

    it('configureMasterPassword should invoke web service',function() {
        expect(encryptionSettingsService.configureMasterPassword).toBeDefined();
        var settings;
        encryptionSettingsService.configureMasterPassword('1234').then(function(result) {
          settings = result;
        });
        $rootScope.$digest();

        expect(settings.path).toBe('/encryption-settings/current');
        expect(settings.method).toBe('PUT');
        expect(settings.entity.masterPassword).toBe('1234');
    });

    it('configureMasterPassword should invoke web service',function() {
        expect(encryptionSettingsService.initializeMasterPassword).toBeDefined();
        var settings;
        encryptionSettingsService.initializeMasterPassword('1234').then(function(result) {
          settings = result;
        });
        $rootScope.$digest();

        expect(settings.path).toBe('/encryption-settings/current');
        expect(settings.method).toBe('POST');
        expect(settings.entity.masterPassword).toBe('1234');
    });

    it('resetMasterPassword should invoke web service',function() {
        expect(encryptionSettingsService.resetMasterPassword).toBeDefined();
        var settings;
        encryptionSettingsService.resetMasterPassword('1234','5678').then(function(result) {
          settings = result;
        });
        $rootScope.$digest();

        expect(settings.path).toBe('/encryption-settings');
        expect(settings.method).toBe('PUT');
        expect(settings.entity.masterPassword).toBe('1234');
        expect(settings.entity.newMasterPassword).toBe('5678');
    });
});
