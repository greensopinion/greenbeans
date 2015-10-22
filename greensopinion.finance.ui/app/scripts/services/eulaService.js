'use strict';

/**
 * @ngdoc service
 * @name greensopinionfinanceApp.eulaService
 * @description
 * # eulaService
 * Service in the greensopinionfinanceApp.
 */
angular.module('greensopinionfinanceApp')
  .service('eulaService', ['rest', function(rest) {
    var API_BASE = '/eula';
    var eulaCheckComplete = false;
    return {
      retrieveCurrentUserEulaStatus: function() {
        return rest.get(API_BASE + '/user-agreements/current');
      },
      putCurrentUserEulaStatus: function(agree) {
        return rest.put(API_BASE + '/user-agreements/current',{ userHasAgreedToLicense: agree });
      },
      retrieveEula: function() {
        return rest.get(API_BASE + '/current');
      },
      isEulaCheckComplete: function() {
        return eulaCheckComplete;
      },
      setEulaCheckComplete: function() {
        eulaCheckComplete = true;
      }
    };
  }]);
