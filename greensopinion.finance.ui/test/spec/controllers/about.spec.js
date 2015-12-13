'use strict';

describe('Controller: AboutCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var AboutCtrl,
    scope, aboutService, eulaService, consoler;

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
            copyrightNotice: 'Copyright (c) 2015 Acme Inc.',
            applicationName: 'This Is It'
          });
        });
      }
    };
    eulaService = {
      retrieveEula: function () {
        return $q(function(resolve){
          resolve({
            text: '<div>EULA</div>'
          });
        });
      }
    };

    AboutCtrl = $controller('AboutCtrl', {
      $scope: scope,
      aboutService: aboutService,
      eulaService: eulaService,
      console: consoler
    });
  }));

  it('should place copyrightNotice and applicationName in scope', function () {
    scope.$digest();
    expect(scope.copyrightNotice).toEqual('Copyright (c) 2015 Acme Inc.');
    expect(scope.applicationName).toEqual('This Is It');
  });

  it('should place eula in scope', inject(function ($sce) {
    scope.$digest();
    expect($sce.getTrustedHtml(scope.eula)).toEqual('<div>EULA</div>');
  }));
});
