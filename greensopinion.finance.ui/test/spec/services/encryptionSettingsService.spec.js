'use strict';

describe('Service: EncryptionSettingsService', function () {

    // load the controller's module
    beforeEach(module('greensopinionfinanceApp'));

    var encryptionSettingsService, mockRest, payload, payloadPromise,$rootScope;

    beforeEach(module(function ($provide) {
      mockRest = {
        get: function(path) {
          payload.method = 'GET';
          payload.path = path;
          return payloadPromise;
        },
        put: function(path,entity) {
          payload.method = 'PUT';
          payload.path = path;
          payload.entity = entity;
          return payloadPromise;
        },
        post: function(path,entity) {
          payload.method = 'POST';
          payload.path = path;
          payload.entity = entity;
          return payloadPromise;
        }
      };
      $provide.value('rest', mockRest);
    }));
    beforeEach(inject(function ($injector,$q,_$rootScope_) {
        $rootScope = _$rootScope_;
        payload = { configured: false, initialized: false };
        payloadPromise = $q(function(resolve){
            resolve(payload);
        });
        encryptionSettingsService = $injector.get('encryptionSettingsService');
    }));

    it('get should invoke web service',inject(function($q) {
        var settings;
        encryptionSettingsService.get().then(function(result) {
          settings = result;
        });
        $rootScope.$digest();

        expect(settings.path).toBe('/encryption-settings/current');
        expect(settings.method).toBe('GET');
        expect(settings.initialized).toBe(false);
        expect(settings.configured).toBe(false);

        payload = { configured: true, initialized: true };
        payloadPromise = $q(function(resolve){
            resolve(payload);
        });
        encryptionSettingsService.get().then(function(result) {
          settings = result;
        });
        $rootScope.$digest();

        expect(settings.initialized).toBe(true);
        expect(settings.configured).toBe(true);
    }));

    it('configureMasterPassword should invoke web service',function() {
        expect(encryptionSettingsService.configureMasterPassword).toBeDefined();
        var settings;
        encryptionSettingsService.configureMasterPassword('1234').then(function(result) {
          settings = result;
        });
        $rootScope.$digest();

        expect(settings.path).toBe('/encryption-settings/current');
        expect(settings.method).toBe('PUT');
        expect(settings.entity.masterPassword).toBe( '1234');
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
        expect(settings.entity.masterPassword).toBe( '1234');
    });
});
