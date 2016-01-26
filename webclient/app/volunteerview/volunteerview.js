'use strict';

var VolunteerCtrl = function($q, $scope, $stateParams, Api) {
  this.q_ = $q;
  this.Api_ = Api;
  this.volunteerId_ = $stateParams.volunteerId;
  this.refresh_($scope);
}

VolunteerCtrl.prototype.refresh_ = function(scope) {
  this.load_(scope);
};

VolunteerCtrl.prototype.load_ = function(scope) {
  this.Api_.Volunteers.get({volunteerId: this.volunteerId_},
      this.onLoadSuccess_.bind(this, scope),
      this.onLoadFailure_.bind(this));
};

VolunteerCtrl.prototype.onLoadSuccess_ = function(scope, volunteer) {
  volunteer = {name: 'Hello1', id:101};
  scope.volunteer = volunteer;
  console.log(volunteer);
};

VolunteerCtrl.prototype.onLoadFailure_ = function(scope) {
  alert('Unable to load volunteer data.');
}

angular.module('myApp.volunteerview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('volunteer', {
    url: '/volunteers/:volunteerId',
    templateUrl: 'volunteerview/volunteerview.html',
    controller: 'VolunteerCtrl'
  });
}])
.controller('VolunteerCtrl', VolunteerCtrl);
