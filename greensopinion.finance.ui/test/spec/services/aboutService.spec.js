'use strict';

describe('Service: AboutService', function () {

    // load the controller's module
    beforeEach(module('greensopinionfinanceApp'));

    var aboutService, mockRest, payload, $rootScope, promiseOf;

    beforeEach(module(function ($provide) {
      mockRest = {
        get: function(path) {
          payload.method = 'GET';
          payload.path = path;
          return promiseOf;
        }
      };
      $provide.value('rest', mockRest);
    }));
    beforeEach(inject(function ($injector,_$rootScope_,$q) {
        $rootScope = _$rootScope_;
        payload = { applicationName: 'App Name', copyrightNotice: 'Copyright Notice' };
        promiseOf = $q(function(resolve){
            resolve(payload);
        });
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
