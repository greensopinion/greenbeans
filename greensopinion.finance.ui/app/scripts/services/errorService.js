'use strict';

angular.module('greensopinionfinanceApp')
  .service('errorService',[function() {
    var getErrorMessage = function(result) {
        if (result instanceof Error) {
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
