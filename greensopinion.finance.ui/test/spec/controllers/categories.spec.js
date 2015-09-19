'use strict';

describe('Controller: CategoriesCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var CategoriesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CategoriesCtrl = $controller('CategoriesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should expose categories', function () {
    expect(scope.categoryList).toBeDefined();
  });
});
