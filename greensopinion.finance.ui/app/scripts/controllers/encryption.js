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
 * @name greensopinionfinanceApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('EncryptionCtrl', ['$scope', 'errorService', 'encryptionSettingsService',
    '$q',
    'ErrorModel', '$location', 'initializationService',
    function($scope, errorService, encryptionSettingsService, $q, ErrorModel,
      $location,
      initializationService) {

      $scope.formData = {
        masterPassword: '',
        masterPassword2: '',
        newMasterPassword: '',
        newMasterPassword2: ''
      };
      var fetchSettings = function() {
        encryptionSettingsService.get().then(function(encryptionSettings) {
          $scope.encryptionSettings = encryptionSettings;

          if (!$scope.needsInitialization() && !$scope.needsConfiguration()) {
            initializationService.initialized(true);
            $location.path('/help');
          }
        });
      };
      var configure = function() {
        if ($scope.formData.masterPassword.trim().length === 0) {
          return $q.reject(new ErrorModel('You must enter a master password.'));
        }
        if ($scope.formData.masterPassword !== $scope.formData.masterPassword2) {
          return $q.reject(new ErrorModel('Passwords entered do not match.'));
        }
        return encryptionSettingsService.configureMasterPassword($scope.formData.masterPassword)
          .then(function(result) {
            fetchSettings();
            return result;
          });
      };

      var initialize = function() {
        if ($scope.formData.masterPassword.trim().length === 0) {
          return $q.reject(new ErrorModel('You must enter a master password.'));
        }
        return encryptionSettingsService.initializeMasterPassword($scope.formData.masterPassword)
          .then(function(result) {
            fetchSettings();
            return result;
          });
      };
      var resetMasterPassword = function() {
        if ($scope.formData.masterPassword.trim().length === 0) {
          return $q.reject(new ErrorModel('You must enter the original master password.'));
        }
        if ($scope.formData.newMasterPassword.trim().length === 0) {
          return $q.reject(new ErrorModel('You must enter a new master password.'));
        }
        if ($scope.formData.newMasterPassword !== $scope.formData.newMasterPassword2) {
          return $q.reject(new ErrorModel('New master passwords entered do not match.'));
        }
        return encryptionSettingsService.resetMasterPassword($scope.formData.masterPassword,$scope.formData.newMasterPassword)
          .then(function(result) {
            fetchSettings();
            return result;
          });
      };

      $scope.needsConfiguration = function() {
        return $scope.encryptionSettings !== undefined && !$scope.encryptionSettings.configured;
      };
      $scope.needsInitialization = function() {
        return $scope.encryptionSettings !== undefined && !$scope.encryptionSettings.initialized &&
          $scope.encryptionSettings.configured;
      };
      $scope.configure = function() {
        return errorService.maintainErrorMessageInScope(configure(), $scope);
      };
      $scope.initialize = function() {
        return errorService.maintainErrorMessageInScope(initialize(), $scope);
      };
      $scope.resetMasterPassword = function() {
        return errorService.maintainErrorMessageInScope(resetMasterPassword(), $scope);
      };
      fetchSettings();
    }
  ]);
