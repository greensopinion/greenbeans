'use strict';

describe('Controller: TransactionsListingCtrl', function () {

  // load the controller's module
  beforeEach(module('greensopinionfinanceApp'));

  var TransactionsListingCtrl,
    scope,$rootScope,$modal,mockReportService, periodTransactions,modalOptions;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_,_$modal_, $q) {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
    $modal = _$modal_;

    periodTransactions = {
      name: 'August 2015',
      transactions: [
        {
          date: '2015-02-28T23:41:00.023Z', description: 'a purchase', amount: -123456
        },
        {
          date: '2015-02-30T23:41:00.023Z', description: 'a purchase2', amount: -140456
        },
        {
          date: '2015-02-30T23:41:00.023Z', description: 'deposit1', amount: 10243
        },
        {
          date: '2015-02-31T23:41:00.023Z', description: 'deposit2', amount: 2043
        }
      ]
    };
    mockReportService = {
      transactionsForMonth: function() {
        return $q(function(resolve) {
          resolve( periodTransactions);
        });
      }
    };
    var fakeModal = {
        result: {
            then: function (confirmCallback, cancelCallback) {
                this.confirmCallBack = confirmCallback;
                this.cancelCallback = cancelCallback;
            }
        },
        close: function () {
            this.result.confirmCallBack();
        },
        dismiss: function () {
            this.result.cancelCallback();
        }
    };
    spyOn(_$modal_, 'open').and.callFake(function(options){
        modalOptions = options;

        return fakeModal;
    });
    TransactionsListingCtrl = $controller('TransactionsListingCtrl', {
      $scope: scope,
      $routeParams: { month: 201508},
      $modal: _$modal_,
      reportService: mockReportService
    });
  }));

  it('should expose title in scope', function () {
    $rootScope.$digest();
    expect(scope.title).toBe('Transactions: August 2015');
  });

  it('should expose incomeOf() in scope',function() {
    expect(scope.incomeOf).toBeDefined();
    expect(scope.incomeOf({amount:100023})).toBe('1000.23');
    expect(scope.incomeOf({amount:-100023})).toBe('');
  });

  it('should expose expenseOf() in scope',function() {
    expect(scope.expenseOf).toBeDefined();
    expect(scope.expenseOf({amount:100023})).toBe('');
    expect(scope.expenseOf({amount:-100023})).toBe('1000.23');
  });

  it('should expose dateOf() in scope',function() {
    expect(scope.dateOf).toBeDefined();
    expect(scope.dateOf({date:'2015-02-28T23:41:00.023Z'})).toBe('2015-02-28');
  });

  it('should expose periodTransactions in scope',function() {
    $rootScope.$digest();
    expect(scope.periodTransactions).toBe(periodTransactions);
  });
  it ('should expose sortCategory() in scope',function() {
    expect(scope.sortCategory).toBeDefined();
    expect(scope.sortCategory({categoryName:'One'})).toBe('One');
  });

  it('should expose sorting',function() {
    expect(scope.switchSortType).toBeDefined();
    expect(scope.sortType).toBeDefined();
    expect(scope.sortIncome).toBeDefined();
    expect(scope.sortExpense).toBeDefined();

    // default
    expect(scope.sortType).toBe(scope.dateOf);
    expect(scope.sortReverse).toBe(false);

    scope.switchSortType(scope.dateOf);
    expect(scope.sortType).toBe(scope.dateOf);
    expect(scope.sortReverse).toBe(true);

    scope.switchSortType(scope.dateOf);
    expect(scope.sortType).toBe(scope.dateOf);
    expect(scope.sortReverse).toBe(false);

    scope.switchSortType(scope.sortIncome);
    expect(scope.sortType).toBe(scope.sortIncome);
    expect(scope.sortReverse).toBe(true);

    scope.switchSortType(scope.sortExpense);
    expect(scope.sortType).toBe(scope.sortExpense);
    expect(scope.sortReverse).toBe(true);
  });

  it('should expose setCategory()', function() {
    expect(scope.setCategory).toBeDefined();
    var transaction = {
      date: '2015-02-28T23:41:00.023Z', description: 'a purchase', amount: -123456
    };

    scope.setCategory(transaction);
    expect(modalOptions.templateUrl).toBe('views/category-dialog.html');
    expect(modalOptions.resolve.transaction()).toBe(transaction);
  });

  it('should expose editTransaction()', function() {
    $rootScope.$digest();
    expect(scope.editTransaction).toBeDefined();

    scope.editTransaction(scope.periodTransactions.transactions[1],true);
    expect(scope.periodTransactions.transactions[1].edit).toEqual(true);

    scope.editTransaction(scope.periodTransactions.transactions[2],true);
    expect(scope.periodTransactions.transactions[1].edit).toEqual(false);
    expect(scope.periodTransactions.transactions[2].edit).toEqual(true);

    scope.editTransaction(scope.periodTransactions.transactions[2],false);
    expect(scope.periodTransactions.transactions[1].edit).toEqual(false);
    expect(scope.periodTransactions.transactions[2].edit).toEqual(false);
  });
});
