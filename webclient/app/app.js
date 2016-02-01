'use strict';

var lh = "http://127.0.0.1:8080";
var loc1="http://192.168.0.5:8080";
var loc2 = 'http://192.168.2.4:8080';
var loc3 = "http://192.168.43.94:8080";
var vm = 'http://188.166.254.184:8080'

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
  'myApp.volunteersview',
  'myApp.volunteerview',
  'myApp.studentsview',
  'myApp.studentview',
  'myApp.questionpaperview',
  'myApp.version',

  'myApp.mockbackend'
])
// localhost should be replaced with backend's ip
.constant('API_BASE_URL', 'www.mtt.com')

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
