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
