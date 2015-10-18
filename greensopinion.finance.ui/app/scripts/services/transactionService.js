'use strict';

/**
 * @ngdoc service
 * @name greensopinionfinanceApp.transactionService
 * @description
 * # transactionService
 * Service in the greensopinionfinanceApp.
 */
angular.module('greensopinionfinanceApp')
  .service('transactionService',['rest', function(rest) {
    var API_BASE = '/transactions';
    return {
      putCategory: function(transaction,categoryName) {
          return rest.put(API_BASE+'/'+encodeURIComponent(transaction.id)+'/category',{name:categoryName});
      }
    };
  }]);
