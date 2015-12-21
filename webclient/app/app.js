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
// localhost should be replaced with backend's ip
.constant('API_BASE_URL', 'http://192.168.2.4:8080')

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
