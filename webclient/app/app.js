'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ui.router',
  'ngResource',
  'ngMaterial',
  'myApp.resources',
  'myApp.authservice',
  'myApp.loginview',
  'myApp.registerview',
  'myApp.customersview',
  'myApp.customerview',
  'myApp.version'
])

.constant('API_BASE_URL', 'http://107.191.47.213:8080')

.config(['$urlRouterProvider', '$mdThemingProvider', '$httpProvider', 
    function($urlRouterProvider, $mdThemingProvider, $httpProvider) {
      // $routeProvider.otherwise({redirectTo: '/login'});
      $urlRouterProvider.otherwise('login');

      $mdThemingProvider.theme('docs-dark', 'default')
        .primaryPalette('yellow')
        .dark();
      
      delete $httpProvider.defaults.headers.common['X-Requested-With'];
      $httpProvider.defaults.withCredentials = true;
}]);
