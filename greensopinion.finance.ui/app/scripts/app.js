'use strict';

/**
 * @ngdoc overview
 * @name greensopinionfinanceApp
 * @description
 * # greensopinionfinanceApp
 *
 * Main module of the application.
 */
angular
  .module('greensopinionfinanceApp', [
    'ngAnimate',
    'ngMessages',
    'ngResource',
    'ngRoute'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/import', {
        templateUrl: 'views/import.html',
        controller: 'ImportCtrl',
        controllerAs: 'import'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
