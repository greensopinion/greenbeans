'use strict';

describe('Controller: CategoryDialogCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var $rootScope,CategoryDialogCtrl,
    scope,categoryList,
    mockCategoryService,
    mockTransactionService,
    modalClosed,
    addedCategoryRule,putCategoryValue;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_,$q) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
    modalClosed = false;

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
      },
      addRuleByName: function(categoryName,rule) {
        addedCategoryRule = {
          categoryName: categoryName,
          rule: rule
        };
        return $q(function(resolve) {
          resolve();
        });
      }
    };
    mockTransactionService = {
      putCategory: function(transaction,categoryName) {
        putCategoryValue = {
          transaction: transaction,
          categoryName: categoryName
        };
        return $q(function(resolve) {
          resolve();
        });
      }
    };

    CategoryDialogCtrl = $controller('CategoryDialogCtrl', {
      $scope: scope,
      $modalInstance: {
        close: function() {
          modalClosed = true;
        }
      },
      categoryService: mockCategoryService,
      transactionService: mockTransactionService,
      transaction: {
        id: 'id123',
        description: 'desc'
      }
    });
  }));

  it('should expose cancel() in scope', function () {
    expect(scope.cancel).toBeDefined();
    scope.cancel();
    expect(modalClosed).toBe(true);
  });

  it('should expose categoryList in scope', function () {
    $rootScope.$digest();
    expect(scope.categoryList).toBe(categoryList);
  });
  it('should expose okMatching() in scope', function () {
    expect(scope.okMatching).toBeDefined();
    scope.categoryName = 'Test Cat';

    scope.okMatching();
    $rootScope.$digest();

    expect(addedCategoryRule).toBeDefined();
    expect(addedCategoryRule.categoryName).toEqual('Test Cat');
    expect(addedCategoryRule.rule.matchDescription).toEqual('desc');

    expect(modalClosed).toBe(true);
  });
  it('should expose okSingle() in scope', function () {
    expect(scope.okSingle).toBeDefined();
    scope.categoryName = 'Test Cat';

    scope.okSingle();
    $rootScope.$digest();

    expect(putCategoryValue).toBeDefined();
    expect(putCategoryValue.transaction).toBe(scope.transaction);
    expect(putCategoryValue.categoryName).toEqual('Test Cat');

    expect(modalClosed).toBe(true);
  });
});
