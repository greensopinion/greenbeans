'use strict';

angular.module('greensopinionfinanceApp')
  .service('Error',function () {
      function Error(message) {
          this.message = message;
      }
      return Error;
  });
