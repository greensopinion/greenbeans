'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('MainCtrl',['$scope','errorService','encryptionSettingsService','$q','ErrorModel', function ($scope,errorService,encryptionSettingsService,$q,ErrorModel) {

    $scope.formData = {
      masterPassword: '',
      masterPassword2: ''
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
          encryptionSettingsService.get().then(function(encryptionSettings) {
            $scope.encryptionSettings = encryptionSettings;
          });
          return result;
      });
    };

    var initialize = function() {
      if ($scope.formData.masterPassword.trim().length === 0) {
          return $q.reject(new ErrorModel('You must enter a master password.'));
      }
      return encryptionSettingsService.initializeMasterPassword($scope.formData.masterPassword)
        .then(function(result) {
          encryptionSettingsService.get().then(function(encryptionSettings) {
            $scope.encryptionSettings = encryptionSettings;
          });
          return result;
      });
    };

    $scope.needsConfiguration = function() {
      return $scope.encryptionSettings !== undefined && !$scope.encryptionSettings.configured;
    };
    $scope.needsInitialization = function() {
      return $scope.encryptionSettings !== undefined && !$scope.encryptionSettings.initialized;
    };
    $scope.configure = function() {
      return errorService.maintainErrorMessageInScope(configure(),$scope);
    };
    $scope.initialize = function() {
      return errorService.maintainErrorMessageInScope(initialize(),$scope);
    };
    encryptionSettingsService.get().then(function(encryptionSettings) {
      $scope.encryptionSettings = encryptionSettings;
    });
  }]);
