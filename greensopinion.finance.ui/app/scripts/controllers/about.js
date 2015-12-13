'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('AboutCtrl',['$scope','$sce','aboutService','eulaService', function ($scope,$sce,aboutService,eulaService) {
    aboutService.about().then(function(about) {
      $scope.copyrightNotice = about.copyrightNotice;
      $scope.applicationName = about.applicationName;
    });
    eulaService.retrieveEula().then(function(eula) {
      $scope.eula = $sce.trustAsHtml(eula.text);
    });
  }]);
