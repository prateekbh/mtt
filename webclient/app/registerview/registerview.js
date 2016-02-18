'use strict';

var RegisterCtrl = function($mdToast, $q, $scope, $state, Api) {
  this.mdToast_ = $mdToast;
  this.q_ = $q;
  this.Api_ = Api;

  $scope.register = this.register_.bind(this, $scope, $state);
}

RegisterCtrl.prototype.register_ = function(scope, state, user) {
  this.validate_(user)
  .then(this.onValidateSuccess_.bind(this, scope, state),
      this.onValidateFailure_.bind(this));
};

RegisterCtrl.prototype.validate_ = function(user) {
  var d = this.q_.defer();
  if (user.vpwd === user.confirm_password) {
    d.resolve(user);
  } else {
    d.reject();
  }
  return d.promise;
};

RegisterCtrl.prototype.onValidateSuccess_ = function(scope, state, user) {
  this.Api_.Register.register(user, this.onRegisterSuccess_.bind(this, scope, state), 
      this.onRegisterFailure_.bind(this, scope));
};

RegisterCtrl.prototype.onValidateFailure_ = function() {
  alert("Password and confirmed password did not match. Please try again.");
};

RegisterCtrl.prototype.onRegisterSuccess_ = function(scope, state) {
  this.mdToast_.show(
    this.mdToast_.simple()
      .content('Registration successfully done. Please login.')
      .position('top right')
      .hideDelay(3000)
   );
  state.go('home');
};

RegisterCtrl.prototype.onRegisterFailure_ = function(scope, error) {
  alert('Given username is already taken. Please choose a different one.');
}

angular.module('myApp.registerview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('register', {
    url: '/register',
    templateUrl: 'registerview/registerview.html',
    controller: 'RegisterCtrl'
  });
}])
.controller('RegisterCtrl', RegisterCtrl);
