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
        errorService.maintainErrorMessageInScope(deferred.promise,scope);
        deferred.reject(new ErrorModel('test message'));
        $rootScope.$digest();
        expect(scope.errorMessage).toBe('test message');
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
        errorService.maintainErrorMessageInScope(deferred.promise,scope);
        deferred.resolve('yay');
        $rootScope.$digest();
        expect(scope.errorMessage).toBeUndefined();
    });
});
