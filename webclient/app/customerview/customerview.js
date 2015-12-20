'use strict';

var CustomerCtrl = function($q, $scope, $stateParams, Api) {
  this.q_ = $q;
  this.Api_ = Api;
  this.customerId_ = $stateParams.customerId;

  this.refresh_($scope);
}

CustomerCtrl.prototype.refresh_ = function(scope) {
  this.load_(scope);
};

CustomerCtrl.prototype.load_ = function(scope) {
  this.Api_.Customers.get({customerId: this.customerId_},
      this.onLoadSuccess_.bind(this, scope),
      this.onLoadFailure_.bind(this));
};

CustomerCtrl.prototype.onLoadSuccess_ = function(scope, customer) {
  customer = {name: 'Hello'};
  scope.customer = customer;
  console.log(customer);
};

CustomerCtrl.prototype.onLoadFailure_ = function(scope) {
  alert('Unable to load customer.');
}

angular.module('myApp.customerview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('customer', {
    url: '/customer/:customerId',
    templateUrl: 'customerview/customerview.html',
    controller: 'CustomerCtrl'
  });
}])
.controller('CustomerCtrl', CustomerCtrl);
