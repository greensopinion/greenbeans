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
