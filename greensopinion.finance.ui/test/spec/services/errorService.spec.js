'use strict';

describe('Service: ErrorService', function () {

    // load the controller's module
    beforeEach(module('greensopinionfinanceApp'));

    var errorService,$rootScope,scope;

    beforeEach(inject(function ($injector,_$rootScope_) {
        $rootScope = _$rootScope_;
        scope = {};
        errorService = $injector.get('errorService');
    }));

    it('should populate errorMessage on failure',function($q) {
        errorService.maintainErrorMessageInScope($q.reject(new Error('test message')),scope);
        $rootScope.$digest();
        expect(scope.errorMessage).toBe('test message');
    });
    it('should clear errorMessage on success',function($q) {
        scope.errorMessage = 'a message';
        errorService.maintainErrorMessageInScope($q.resolve('yay'),scope);
        $rootScope.$digest();
        expect(scope.errorMessage).toBeUndefined();
    });
});
