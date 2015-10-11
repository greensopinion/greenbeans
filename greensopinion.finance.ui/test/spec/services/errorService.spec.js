'use strict';

describe('Service: ErrorService', function () {

    // load the controller's module
    beforeEach(module('greensopinionfinanceApp'));

    var errorService,$rootScope,scope,deferred;

    beforeEach(inject(function ($injector,_$rootScope_, $q) {
        $rootScope = _$rootScope_;
        scope = {};
        deferred = $q.defer();
        errorService = $injector.get('errorService');
    }));

    it('should populate errorMessage on failure',inject(function(ErrorModel) {
        var successCalled = false;
        var failureCalled = false;
        errorService.maintainErrorMessageInScope(deferred.promise,scope).then(function() {
          successCalled = true;
        },function() {
          failureCalled = true;
        });
        deferred.reject(new ErrorModel('test message'));
        $rootScope.$digest();
        expect(scope.errorMessage).toBe('test message');
        expect(successCalled).toBe(false);
        expect(failureCalled).toBe(true);
    }));
    it('should handle REST failure message payload',function() {
        errorService.maintainErrorMessageInScope(deferred.promise,scope);
        deferred.reject({
          errorCode: 500,
          message: 'some error message'
        });
        $rootScope.$digest();
        expect(scope.errorMessage).toBe('some error message');
    });
    it('should clear errorMessage on success',function() {
        scope.errorMessage = 'a message';
        var successCalled = false;
        var failureCalled = false;
        errorService.maintainErrorMessageInScope(deferred.promise,scope).then(function() {
          successCalled = true;
        },function() {
          failureCalled = true;
        });
        deferred.resolve('yay');
        $rootScope.$digest();
        expect(scope.errorMessage).toBeUndefined();
        expect(successCalled).toBe(true);
        expect(failureCalled).toBe(false);
    });
});
