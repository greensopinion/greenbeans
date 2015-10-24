'use strict';

angular.module('greensopinionfinanceApp')
  .service('errorService',['$q','ErrorModel',function($q,ErrorModel) {
    var getErrorMessage = function(result) {
        if (result instanceof ErrorModel) {
          return result.message;
        }
        if (result.errorCode !== undefined) {
          return result.message;
        }
        return 'Unexpected error: '+result;
    };
    return {
      clearErrorMessage: function(scope) {
        delete scope.errorMessage;
      },
      maintainErrorMessageInScope: function(promise,scope) {
          return promise.then(function success(result) {
              delete scope.errorMessage;
              return $q(function(resolve) {
                resolve(result);
              });
          },function failure(result) {
              scope.errorMessage = getErrorMessage(result);
              console.log('Error: '+JSON.stringify(result));
              return $q(function(resolve, reject) {
                reject(result);
              });
          });
      }
    };
  }]);
