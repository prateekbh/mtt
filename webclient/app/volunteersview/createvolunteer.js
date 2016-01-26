'use strict';

var CreateVolunteerCtrl = function($scope, $mdDialog, $q, $mdToast, Api) {
  this.q_ = $q;
  this.Api_ = Api;
  this.mdDialog_ = $mdDialog;

  $scope.create = this.create_.bind(this, $mdToast);
  $scope.close = this.close_.bind(this);
}

CreateVolunteerCtrl.prototype.create_ = function(mdToast, volunteer) {
  this.validate_(volunteer).then(this.onValidateSuccess_.bind(
        this, mdToast, volunteer), this.onValidateFailure_.bind(this));
};

CreateVolunteerCtrl.prototype.validate_ = function(c) {
  var d = this.q_.defer();
  if (!c.name || !c.trNumber || !c.mobile || !c.vehicle) {
    d.reject();
  } else {
    d.resolve();
  }
  return d.promise;
};

CreateVolunteerCtrl.prototype.onValidateSuccess_ = function(mdToast, volunteer) {
  this.Api_.Volunteers.post(volunteer, this.onCreateSuccess_.bind(this, mdToast, volunteer),
      this.onCreateFailure_.bind(this));
};

CreateVolunteerCtrl.prototype.onValidateFailure_ = function() {
};

CreateVolunteerCtrl.prototype.onCreateSuccess_ = function(mdToast, volunteer) {
  mdToast.show(
    mdToast.simple()
      .content('Volunteer successfully created.')
      .position('top right')
      .hideDelay(3000)
   );
  this.mdDialog_.hide(volunteer);
};

CreateVolunteerCtrl.prototype.onCreateFailure_ = function() {
  
};

CreateVolunteerCtrl.prototype.close_ = function() {
  this.mdDialog_.hide();
};

angular.module('myApp.volunteersview').controller('CreateVolunteerCtrl', CreateVolunteerCtrl);
