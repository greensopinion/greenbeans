'use strict';

angular.module('greensopinionfinanceApp')
  .service('errorService',['ErrorModel',function(ErrorModel) {
    var getErrorMessage = function(result) {
        if (result instanceof ErrorModel) {
          return result.message;
        }
        return 'Unexpected error: '+result;
    };
    return {
      maintainErrorMessageInScope: function(promise,scope) {
          return promise.then(function success(result) {
              delete scope.errorMessage;
              return result;
          },function failure(result) {
              scope.errorMessage = getErrorMessage(result);
              return result;
          });
      }
    };
  }]);
