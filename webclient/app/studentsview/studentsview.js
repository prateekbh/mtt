// 'use strict';

var StudentsCtrl = function($q, $mdDialog, $scope, $state, Api) {
  this.q_ = $q;
  this.Api_ = Api;

  this.mdDialog_ = $mdDialog;

  $scope.show = this.show_.bind(this, $state);
  $scope.openNewStudentModal = this.openNewStudentModal_.bind(this, $scope, $mdDialog);
  // $scope.students = this.sample();
  this.refresh_($scope);
}

StudentsCtrl.prototype.refresh_ = function(scope) {
  this.load_(scope);
};

StudentsCtrl.prototype.load_ = function(scope) {
  // setTimeout(this.onLoadSuccess_.bind(this, scope), 2000);
  this.Api_.Students.query(this.onLoadSuccess_.bind(this, scope), 
      this.onLoadFailure_.bind(this));
};

StudentsCtrl.prototype.show_ = function(state, student) {
  console.log(student);
  state.go('student', {studentId: student.id});
};

StudentsCtrl.prototype.create = function() {
  this.mdDialog_.cancel();
};

StudentsCtrl.prototype.openNewStudentModal_ = function(scope, mdDialog, ev) {
  mdDialog.show({
    controller: 'AddStudentCtrl',
    templateUrl: 'studentsview/createstudent.html',
    parent: angular.element(document.body),
    targetEvent: ev,
    locals: {parentCtrl: this},
    clickOutsideToClose: true,
    onRemoving: this.refresh_.bind(this, scope),
    fullscreen: true,
  });
};


StudentsCtrl.prototype.sample = function() {
return [{
    id: 1,
    name: 'Govardhan Reddy',
    tr_number: 'TS15TR1234'
  },{
    id: 2,
    name: 'Santosh',
    tr_number: 'TS20TR1234',
    paper_corrected: true,
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
  },{
    id: 1,
    name: 'Govardhan Reddy',
    tr_number: 'TS15TR1234',
    paper_corrected: true,
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
  },
  
  ];

};

StudentsCtrl.prototype.onLoadSuccess_ = function(scope, students) {
  // console.log(students); 
  scope.students = students;
};

StudentsCtrl.prototype.onLoadFailure_ = function(scope) {
  alert('Please check the login credentials.');
  // scope.students = this.sample();
}

angular.module('myApp.studentsview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('students', {
    url: '/students',
    templateUrl: 'studentsview/studentsview.html',
    controller: 'StudentsCtrl'
  });
}])
.controller('StudentsCtrl', StudentsCtrl);
