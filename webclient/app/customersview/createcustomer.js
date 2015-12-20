'use strict';

var CreateCustomerCtrl = function($scope, $mdDialog, $q, $mdToast, Api) {
  this.q_ = $q;
  this.Api_ = Api;
  this.mdDialog_ = $mdDialog;

  $scope.create = this.create_.bind(this, $mdToast);
  $scope.close = this.close_.bind(this);
}

CreateCustomerCtrl.prototype.create_ = function(mdToast, customer) {
  this.validate_(customer).then(this.onValidateSuccess_.bind(
        this, mdToast, customer), this.onValidateFailure_.bind(this));
};

CreateCustomerCtrl.prototype.validate_ = function(c) {
  var d = this.q_.defer();
  if (!c.name || !c.trNumber || !c.mobile || !c.vehicle) {
    d.reject();
  } else {
    d.resolve();
  }
  return d.promise;
};

CreateCustomerCtrl.prototype.onValidateSuccess_ = function(mdToast, customer) {
  this.Api_.Customers.post(customer, this.onCreateSuccess_.bind(this, mdToast, customer),
      this.onCreateFailure_.bind(this));
};

CreateCustomerCtrl.prototype.onValidateFailure_ = function() {
};

CreateCustomerCtrl.prototype.onCreateSuccess_ = function(mdToast, customer) {
  mdToast.show(
    mdToast.simple()
      .content('Customer successfully created.')
      .position('top right')
      .hideDelay(3000)
   );
  this.mdDialog_.hide(customer);
};

CreateCustomerCtrl.prototype.onCreateFailure_ = function() {
  
};

CreateCustomerCtrl.prototype.close_ = function() {
  this.mdDialog_.hide();
};

angular.module('myApp.customersview').controller('CreateCustomerCtrl', CreateCustomerCtrl);
