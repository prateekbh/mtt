'use strict';

var VolunteersCtrl = function($q, $mdDialog, $scope, $state, Api) {
  this.q_ = $q;
  this.Api_ = Api;

  this.mdDialog_ = $mdDialog;

  $scope.show = this.show_.bind(this, $state);
  $scope.openNewVolunteerModal = this.openNewVolunteerModal_.bind(this, $mdDialog);
  this.refresh_($scope);
}

VolunteersCtrl.prototype.refresh_ = function(scope) {
  this.load_(scope);
};

VolunteersCtrl.prototype.load_ = function(scope) {
  setTimeout(this.onLoadSuccess_.bind(this, scope), 2000);
  this.Api_.Volunteers.query(this.onLoadSuccess_.bind(this, scope),
      this.onLoadFailure_.bind(this));
};

VolunteersCtrl.prototype.show_ = function(state, volunteer) {
  console.log(volunteer);
  state.go('volunteer', {volunteerId: volunteer.tr_number});
};

VolunteersCtrl.prototype.create = function() {
  this.mdDialog_.cancel();
};

VolunteersCtrl.prototype.openNewVolunteerModal_ = function(mdDialog, ev) {
  mdDialog.show({
    controller: 'AddStudentCtrl',
    templateUrl: 'volunteersview/createvolunteer.html',
    parent: angular.element(document.body),
    targetEvent: ev,
    locals: {parentCtrl: this},
    clickOutsideToClose: true,
  });
};

VolunteersCtrl.prototype.onLoadSuccess_ = function(scope, volunteers) {
   console.log(volunteers);
  scope.volunteers = [{
    id: 1,
    name: 'Govardhan M',
    center: 'CPD'
  },{
    id: 2,
    name: 'Santosh',
    center: 'Grpt'
  },];
};

VolunteersCtrl.prototype.onLoadFailure_ = function(scope) {
  alert('Please check the login credentials.');
}

angular.module('myApp.volunteersview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('volunteers', {
    url: '/volunteers',
    templateUrl: 'volunteersview/volunteersview.html',
    controller: 'VolunteersCtrl'
  });
}])
.controller('VolunteersCtrl', VolunteersCtrl);
