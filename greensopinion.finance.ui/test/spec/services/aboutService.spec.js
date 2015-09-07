'use strict';

describe('Service: AboutService', function () {

    // load the controller's module
    beforeEach(module('greensopinionfinanceApp'));

    var aboutService, mockRest, payload;

    beforeEach(module(function ($provide) {
      mockRest = {
        get: function(path) {
          payload.path = path;
          return payload;
        }
      };
      $provide.value('rest', mockRest);
    }));
    beforeEach(inject(function ($injector) {
        payload = {};
        aboutService = $injector.get('aboutService');
    }));

    it('about should invoke web service',function() {
        payload = { applicationName: 'App Name', copyrightNotice: 'Copyright Notice'};
        var about = aboutService.about();
        expect(about.path).toBe('/abouts/current');
        expect(about.applicationName).toBe('App Name');
        expect(about.copyrightNotice).toBe('Copyright Notice');
    });
});
