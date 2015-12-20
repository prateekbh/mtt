'use strict';

var AuthService = function($window, $q, Api) {
  this.store_ = $window.localStorage;
  this.q_ = $q;
  this.Api_ = Api;
};

AuthService.prototype.login = function(user) {
  var d = this.q_.defer();
  this.Api_.Login.login(user, 
    this.loginSuccess_.bind(this, d),
    this.loginFailure_.bind(this, d));
  return d.promise;
};


AuthService.prototype.loginSuccess_ = function(d, user) {
  this.store_.setItem('user_name', user.user_name);
  d.resolve(user);
};

AuthService.prototype.loginFailure_ = function(d, errMsg) {
  d.reject(errMsg);
};


angular.module('myApp.authservice', []).factory('AuthService', AuthServiceModule); 
AuthServiceModule.$inject = ['$window', '$q', 'Api'];
function AuthServiceModule($window, $q, Api) { 
  return new AuthService($window, $q, Api);
};
