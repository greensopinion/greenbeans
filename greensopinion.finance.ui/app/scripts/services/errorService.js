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
