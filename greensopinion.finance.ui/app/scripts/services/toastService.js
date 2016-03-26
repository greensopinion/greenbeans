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

/**
 * @ngdoc service
 * @name greensopinionfinanceApp.toastService
 * @description
 * # toastService
 * Service in the greensopinionfinanceApp.
 */
angular.module('greensopinionfinanceApp')
  .service('toastService',['$timeout', function ($timeout) {
    var messageText = '';
    var clearMessage = function(textToMatch) {
      if (textToMatch === undefined || messageText === textToMatch) {
        messageText = '';
      }
    };
    return {
      show: function(text) {
        messageText = text;
        $timeout(function() {
          clearMessage(text);
        },3500);
      },
      clearMessage: clearMessage,
      message: function() {
        return messageText;
      }
    };
  }]);
