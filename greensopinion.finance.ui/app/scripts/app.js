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
    'ngRoute',
    'ui.bootstrap',
    'chart.js'
  ])
  .config(function($routeProvider) {
    $routeProvider
      .when('/encryption', {
        templateUrl: 'views/encryption.html',
        controller: 'EncryptionCtrl',
        controllerAs: 'encryption'
      })
      .when('/eula-agreement', {
        templateUrl: 'views/eula-agreement.html',
        controller: 'EulaAgreementCtrl',
        controllerAs: 'eula-agreement'
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
      .when('/transactions', {
        templateUrl: 'views/transactions.html',
        controller: 'TransactionsCtrl',
        controllerAs: 'transactions'
      })
      .when('/categories', {
        templateUrl: 'views/categories.html',
        controller: 'CategoriesCtrl',
        controllerAs: 'categories'
      })
      .when('/transactions-listing/:month', {
        templateUrl: 'views/transactions-listing.html',
        controller: 'TransactionsListingCtrl',
        controllerAs: 'transactions-listing'
      })
      .when('/reports', {
        templateUrl: 'views/reports.html',
        controller: 'ReportsCtrl',
        controllerAs: 'reports'
      })
      .when('/month-detail/:month', {
        templateUrl: 'views/month-detail.html',
        controller: 'MonthDetailCtrl',
        controllerAs: 'month-detail'
      })
      .otherwise({
        redirectTo: '/eula-agreement'
      });
  });
