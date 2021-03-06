'use strict';

var LoginCtrl = function($scope, $state, Api) {
  this.Api_ = Api;

  $scope.login = this.login_.bind(this, $scope, $state);
  $scope.register = this.register_.bind(this, $state);
}

LoginCtrl.prototype.login_ = function(scope, state, user) {
  this.Api_.Login.login(user, this.onLoginSuccess_.bind(this, scope, state), 
      this.onLoginFailure_.bind(this, scope));
};

LoginCtrl.prototype.register_ = function(state) {
  state.go('register');
};

LoginCtrl.prototype.onLoginSuccess_ = function(scope, state) {
  state.go('home');
  console.log('Login Success.');
};

LoginCtrl.prototype.onLoginFailure_ = function(scope) {
  alert('Login failed. Please check the login credentials.');
}

angular.module('myApp.loginview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('login', {
    url: '/login',
    templateUrl: 'loginview/loginview.html',
    controller: 'LoginCtrl'
  });
}])
.controller('LoginCtrl', LoginCtrl);
