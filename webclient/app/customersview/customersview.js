'use strict';

var CustomersCtrl = function($q, $mdDialog, $scope, $state, Api) {
  this.q_ = $q;
  this.Api_ = Api;

  this.mdDialog_ = $mdDialog;

  $scope.show = this.show_.bind(this, $state);
  $scope.openNewCustomerModal = this.openNewCustomerModal_.bind(this, $mdDialog);
  this.refresh_($scope);
}

CustomersCtrl.prototype.refresh_ = function(scope) {
  this.load_(scope);
};

CustomersCtrl.prototype.load_ = function(scope) {
  setTimeout(this.onLoadSuccess_.bind(this, scope), 2000);
  this.Api_.Customers.query(this.onLoadSuccess_.bind(this, scope), 
      this.onLoadFailure_.bind(this));
};

CustomersCtrl.prototype.show_ = function(state, customer) {
  console.log(customer);
  state.go('customer', {customerId: customer.tr_number});
};

CustomersCtrl.prototype.create = function() {
  this.mdDialog_.cancel();
};

CustomersCtrl.prototype.openNewCustomerModal_ = function(mdDialog, ev) {
  mdDialog.show({
    controller: 'CreateCustomerCtrl',
    templateUrl: 'customersview/createcustomer.html',
    parent: angular.element(document.body),
    targetEvent: ev,
    locals: {parentCtrl: this},
    clickOutsideToClose: true,
  });
};

CustomersCtrl.prototype.onLoadSuccess_ = function(scope, customers) {
  // console.log(customers); 
  scope.customers = [{
    id: 1,
    name: 'Govardhan Reddy',
    tr_number: 'TS15TR1234'
  },{
    id: 2,
    name: 'Santosh',
    tr_number: 'TS20TR1234'
  },{
    id: 3,
    name: 'Chaitanya Reddy',
    tr_number: 'TS19TR1234'
  },{
    id: 4,
    name: 'Gopal',
    tr_number: 'TS19TR1234'
  },{
    id: 5,
    name: 'Suresh B',
    tr_number: 'TS81TR1234'
  },];
};

CustomersCtrl.prototype.onLoadFailure_ = function(scope) {
  alert('Please check the login credentials.');
}

angular.module('myApp.customersview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('customers', {
    url: '/customers',
    templateUrl: 'customersview/customersview.html',
    controller: 'CustomersCtrl'
  });
}])
.controller('CustomersCtrl', CustomersCtrl);
