'use strict';

var StudentCtrl = function($q, $scope, $stateParams, $state, Api) {
  this.q_ = $q;
  this.Api_ = Api;
  this.studentId_ = $stateParams.studentId;

  $scope.correctQuestionPaper = this.correctQuestionPaper_.bind(this, $state);

  // $scope.student = {name: 'Chaitanya', question_paper_code: '1234567', paper_corrected: false};
  this.refresh_($scope);
}

StudentCtrl.prototype.refresh_ = function(scope) {
  this.load_(scope);
};

StudentCtrl.prototype.load_ = function(scope) {
  this.Api_.Students.get({studentId: this.studentId_},
      this.onLoadSuccess_.bind(this, scope),
      this.onLoadFailure_.bind(this));
};

StudentCtrl.prototype.onLoadSuccess_ = function(scope, student) {
  // student = {name: 'Hello'};
  scope.student = student;
  console.log(student);
};

StudentCtrl.prototype.onLoadFailure_ = function(scope) {
  alert('Unable to load student.');
  // this.onLoadSuccess_(scope);
}

StudentCtrl.prototype.correctQuestionPaper_ = function(state, student) {
  state.go('questionpaper', {
    studentId: this.studentId_,
    questionPaperCode: student.question_paper_code
  });
}

angular.module('myApp.studentview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('student', {
    url: '/student/:studentId',
    templateUrl: 'studentview/studentview.html',
    controller: 'StudentCtrl'
  });
}])
.controller('StudentCtrl', StudentCtrl);
