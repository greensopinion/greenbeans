'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:HelpHelpCtrl
 * @description
 * # HelpHelpCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('HelpCtrl',['$scope','$location','$anchorScroll','aboutService', function ($scope,$location,$anchorScroll,aboutService) {
    aboutService.about().then(function(about) {
      $scope.copyrightNotice = about.copyrightNotice;
      $scope.applicationName = about.applicationName;
    });
    $scope.scrollTo = function (id) {
      $location.hash(id);
      $anchorScroll.yOffset = 55;
      $anchorScroll();
    };
  }]);
