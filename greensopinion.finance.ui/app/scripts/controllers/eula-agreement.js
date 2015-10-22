'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:EulaagreementCtrl
 * @description
 * # EulaagreementCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('EulaAgreementCtrl', ['$scope', '$location', '$sce', 'eulaService','errorService', function($scope,
    $location, $sce,
    eulaService,errorService) {

    var completeEulaCheckSuccessfully = function( ){
      eulaService.setEulaCheckComplete();
      $location.path('/encryption');
    };

    eulaService.retrieveCurrentUserEulaStatus().then(function(eulaStatus) {
      $scope.eulaStatus = eulaStatus;
      if (eulaStatus.userHasAgreedToLicense) {
        completeEulaCheckSuccessfully();
      } else {
        eulaService.retrieveEula().then(function(eula) {
          $scope.eula = $sce.trustAsHtml(eula.text);
        });
      }
    });
    var updateStatus = function(agree) {
      errorService.maintainErrorMessageInScope(eulaService.putCurrentUserEulaStatus(agree),$scope).then(function() {
        completeEulaCheckSuccessfully();
      });
    };
    $scope.agree = function() {
        updateStatus(true);
    };
    $scope.disagree = function() {
        updateStatus(false);
    };
  }]);
