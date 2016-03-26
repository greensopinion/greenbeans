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
