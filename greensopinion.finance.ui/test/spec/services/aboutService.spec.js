'use strict';

describe('Service: AboutService', function () {

    // load the controller's module
    beforeEach(module('greensopinionfinanceApp'));

    var aboutService, mockRest, payload, $rootScope;

    beforeEach(function() {
      var resolvePayloadPromise;
      module(function ($provide) {
        mockRest = {
          get: function(path) {
            payload.path = path;
            return resolvePayloadPromise;
          }
        };
        $provide.value('rest', mockRest);
      });
      beforeEach(inject(function (_$rootScope_,$injector,$q) {
          $rootScope = _$rootScope_;
          payload = { applicationName: 'App Name', copyrightNotice: 'Copyright Notice'};
          resolvePayloadPromise = $q(function(resolve) {
            resolve(payload);
          });
          aboutService = $injector.get('aboutService');
      }));
    });

    it('about should invoke web service',function() {
        var about;
        aboutService.about().then(function(result) {
          about = result;
        });
        $rootScope.$digest();
        expect(about.path).toBe('/abouts/current');
        expect(about.applicationName).toBe('App Name');
        expect(about.copyrightNotice).toBe('Copyright Notice');
    });
});
