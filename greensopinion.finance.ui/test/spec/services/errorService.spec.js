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
    it('should expose clearErrorMessage()',function() {
      expect(errorService.clearErrorMessage).toBeDefined();

      scope.errorMessage = 'a message';

      errorService.clearErrorMessage(scope);
      expect(scope.errorMessage).toBeUndefined();
    });
});
