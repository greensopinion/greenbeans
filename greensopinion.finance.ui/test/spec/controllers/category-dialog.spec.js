'use strict';

describe('Controller: CategoryDialogCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var CategoryDialogCtrl,
    scope,categoryList,mockCategoryService;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope,$q) {
    scope = $rootScope.$new();

    categoryList = [
      {
        name: 'One'
      },
      {
        name: 'Two'
      }
    ];
    mockCategoryService = {
      list: function() {
        return $q(function(resolve) {
          resolve(categoryList);
        });
      }
    };

    CategoryDialogCtrl = $controller('CategoryDialogCtrl', {
      $scope: scope,
      $modalInstance: {},
      categoryService: mockCategoryService,
      transaction: {}
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {

  });
});
