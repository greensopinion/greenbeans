'use strict';

describe('Controller: CategoriesCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var CategoriesCtrl,
    scope,$rootScope,mockCategoryService,categoryList;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, $q) {
    $rootScope = _$rootScope_;
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

    CategoriesCtrl = $controller('CategoriesCtrl', {
      $scope: scope,
      categoryService: mockCategoryService
    });
  }));

  it('should expose categories', function () {
    $rootScope.$digest();
    expect(scope.categoryList).toBe(categoryList);
  });
});
