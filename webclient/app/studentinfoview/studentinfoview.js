'use strict';

var StudentInfoCtrl = function($scope, $q, $state, $mdDialog, Api) {
  this.q_ = $q;
  this.Api_ = Api;
  this.mdDialog_ = $mdDialog; 

  $scope.submit = this.submit_.bind(this, $scope, $state);
};

StudentInfoCtrl.prototype.submit_ = function(scope, state, student, ev) {
  this.Api_.Students.get({studentId: student.studentId},
    this.onSubmitSuccess_.bind(this, state, ev),
    this.onSubmitFailure_.bind(this));
};

StudentInfoCtrl.prototype.onSubmitSuccess_ = function(state, ev, student) {
  console.log("student name " + student.name + " qpcode " + student.questionPaperCode);
  var confirm = this.mdDialog_.confirm()
        .title('Please confirm the student details')
        .textContent('Student id ' + student.id + ' name: ' + student.name + ' question paper id: ' + student.questionPaperCode)
        .targetEvent(ev)
        .ok('Confirm')
        .cancel('Incorrect');
  this.mdDialog_.show(confirm).then(function() {
    state.go('questionpaper', {questionPaperCode: student.questionPaperCode});
  }, function() {
    alert('Please try again!');
  });
};

StudentInfoCtrl.prototype.onSubmitFailure_ = function() {
  alert('No student available with the name.');
};

angular.module('myApp.studentinfo', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('studentinfo', {
    url: '/studentinfo',
    templateUrl: 'studentinfoview/studentinfoview.html',
    controller: 'StudentInfoCtrl'
  });
}])
.controller('StudentInfoCtrl', StudentInfoCtrl);
