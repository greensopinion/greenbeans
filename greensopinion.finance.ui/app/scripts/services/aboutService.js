'use strict';

angular.module('greensopinionfinanceApp')
  .service('aboutService',['rest',function(rest) {
    var API_BASE = '/abouts/';
    return {
      about: function() {
          return rest.get(API_BASE+'current');
      }
    };
  }]);
