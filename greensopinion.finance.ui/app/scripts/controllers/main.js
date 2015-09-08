'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('MainCtrl',['$scope','errorService','encryptionSettingsService','$q', function ($scope,errorService,encryptionSettingsService,$q) {
    $scope.needsConfiguration = function() {
      return $scope.encryptionSettings !== undefined && !$scope.encryptionSettings.configured;
    };
    $scope.formData = {
      masterPassword: '',
      masterPassword2: ''
    };
    var configure = function() {
      if ($scope.formData.masterPassword.trim().length === 0) {
          return $q.reject(new Error('You must enter a master password.'));
      }
      if ($scope.formData.masterPassword !== $scope.formData.masterPassword2) {
          return $q.reject(new Error('Passwords entered do not match.'));
      }
      return $q.resolve('placeholder FIXME');
    };
    $scope.configure = function() {
      return errorService.maintainErrorMessageInScope(configure(),$scope);
    };
    encryptionSettingsService.get().then(function(encryptionSettings) {
      $scope.encryptionSettings = encryptionSettings;
    });
  }]);
