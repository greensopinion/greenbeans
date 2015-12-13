'use strict';

/**
 * @ngdoc function
 * @name greensopinionfinanceApp.controller:HelpHelpCtrl
 * @description
 * # HelpHelpCtrl
 * Controller of the greensopinionfinanceApp
 */
angular.module('greensopinionfinanceApp')
  .controller('HelpCtrl',['$scope','$location','$anchorScroll','$timeout','aboutService', function ($scope,$location,$anchorScroll,$timeout,aboutService) {
    aboutService.about().then(function(about) {
      $scope.copyrightNotice = about.copyrightNotice;
      $scope.applicationName = about.applicationName;
    });
    $scope.scrollTo = function (id) {
      $location.hash(id);
      $timeout(function(){
        $location.hash(id);
        $anchorScroll.yOffset = 55;
        $anchorScroll();
      },0,false);
    };
  }]);
