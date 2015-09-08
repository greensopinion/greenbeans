'use strict';

describe('Controller: AboutCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var AboutCtrl,
    scope, aboutService,consoler;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope,$q) {
    scope = $rootScope.$new();

    consoler = {
      log: function(message) {
        console.log(message);
      }
    };

    aboutService = {
      about: function() {
        return $q(function(resolve){
          resolve({
            copyrightNotice: 'Copyright (c) 2015',
            applicationName: 'This Is It'
          });
        });
      }
    };

    AboutCtrl = $controller('AboutCtrl', {
      $scope: scope,
      aboutService: aboutService,
      console: consoler
    });
  }));

  it('should have tests', function () {

  });
});
