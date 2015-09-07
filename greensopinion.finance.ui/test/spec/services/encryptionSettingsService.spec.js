'use strict';

describe('Service: EncryptionSettingsService', function () {

    // load the controller's module
    beforeEach(module('greensopinionfinanceApp'));

    var encryptionSettingsService, mockRest, payload, payloadPromise;

    beforeEach(module(function ($provide) {
      mockRest = {
        get: function(path) {
          payload.path = path;
          return payloadPromise;
        }
      };
      $provide.value('rest', mockRest);
    }));
    beforeEach(inject(function ($injector,$q) {
        payload = { configured: false, initialized: false };
        payloadPromise = $q(function(resolve){
            resolve(payload);
        });
        encryptionSettingsService = $injector.get('encryptionSettingsService');
    }));

    it('about should invoke web service',function($q) {
        var settings;
        encryptionSettingsService.get().then(function(result) {
          settings = result;
        });

        expect(settings.path).toBe('/encryption-settings/current');
        expect(settings.initialized).toBe(false);
        expect(settings.configured).toBe(false);

        payload = { configured: true, initialized: true };
        payloadPromise = $q(function(resolve){
            resolve(payload);
        });
        encryptionSettingsService.get().then(function(result) {
          settings = result;
        });

        expect(settings.initialized).toBe(true);
        expect(settings.configured).toBe(true);
    });
});
