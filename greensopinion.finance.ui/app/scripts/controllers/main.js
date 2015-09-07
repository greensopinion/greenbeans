'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('MainCtrl',['$scope','encryptionSettingsService', function ($scope,encryptionSettingsService) {
    $scope.needsConfiguration = function() {
      return $scope.encryptionSettings !== undefined && !$scope.encryptionSettings.configured;
    };
    $scope.formData = {
      masterPassword: '',
      masterPassword2: ''
    };
    $scope.configure = function() {};
    encryptionSettingsService.get().then(function(encryptionSettings) {
      $scope.encryptionSettings = encryptionSettings;
    });
  }]);
