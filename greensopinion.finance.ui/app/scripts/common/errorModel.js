'use strict';

angular.module('greensopinionfinanceApp')
  .service('ErrorModel',function () {
      function ErrorModel(message) {
          this.message = message;
      }
      ErrorModel.prototype = Object.create(Error.prototype);
      return ErrorModel;
  });
