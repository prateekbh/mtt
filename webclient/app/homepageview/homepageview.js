'use strict';

var HomepageCtrl = function($q, $scope, $state, $stateParams, Api) {
  $scope.createstudent = this.createStudent_.bind(this, $state);
  $scope.correctpaper = this.correctPaper_.bind(this, $state);
}

HomepageCtrl.prototype.createStudent_ = function(state) {
  state.go('createstudent');
};

HomepageCtrl.prototype.correctPaper_ = function(state) {
  state.go('studentinfo');
};

angular.module('myApp.homepageview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('home', {
    url: '/home',
    templateUrl: 'homepageview/homepageview.html',
    controller: 'HomepageCtrl'
  });
}])
.controller('HomepageCtrl', HomepageCtrl);
