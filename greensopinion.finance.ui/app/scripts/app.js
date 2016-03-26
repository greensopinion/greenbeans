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


      .when('/help', {
        templateUrl: 'views/help/help-index.html',
        controller: 'HelpCtrl',
        controllerAs: 'help'
      })

      .otherwise({
        redirectTo: '/eula-agreement'
      });
  })
  .run(function($rootScope,$location,$anchorScroll,$routeParams,$timeout) {
    $rootScope.$on('$routeChangeSuccess', function() {
      var scrollTo = $location.hash();
      if (scrollTo) {
        $timeout(function() {
          $location.hash(scrollTo);
          $anchorScroll.yOffset = 55;
          $anchorScroll();
        },150,false);
      }
    });
  });
