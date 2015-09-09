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
