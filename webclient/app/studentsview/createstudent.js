'use strict';

var AddStudentCtrl = function($scope, $q, $mdToast, $state, Api) {
  this.q_ = $q;
  this.Api_ = Api;

  // Auto complete of school names.
  $scope.searchSchoolName = null;
  $scope.searchSchoolNameChange = this.searchSchoolNameChange_.bind(this);
  $scope.selectedSchoolNameItemChange = this.selectedSchoolNameItemChange_.bind(this);
  $scope.querySearchSchoolName = this.querySearchSchoolName_.bind(this, $scope);
  
  // Auto complete of places.
  $scope.searchPlace = null;
  $scope.searchPlaceChange = this.searchPlaceChange_.bind(this);
  $scope.selectedPlaceItemChange = this.selectedPlaceItemChange_.bind(this);
  $scope.querySearchPlace = this.querySearchPlace_.bind(this, $scope);
  
  $scope.create = this.create_.bind(this, $mdToast, $state);
  
  $scope.test_centers = [
    'Test Center 1',
    'Test Center 2',
    'Test Center 3',
    'Test Center 4',
  ];
}

AddStudentCtrl.prototype.create_ = function(mdToast, state, student) {
  this.validate_(student).then(this.onValidateSuccess_.bind(
        this, mdToast, state, student), this.onValidateFailure_.bind(this));
};

AddStudentCtrl.prototype.validate_ = function(c) {
  var d = this.q_.defer();
  var compulsoryFields = ['studentName', 'schoolId', 'centerId', 'questionPaperCode']
  var validateCount = 0;
  for (var i = 0; i < compulsoryFields.length; i++) {
    if (!c[compulsoryFields[i]]) {
      validateCount += 1;
      d.reject(compulsoryFields[i]);
    }
  }
  if (validateCount == 0) {
    d.resolve();
  }
  return d.promise;
};

AddStudentCtrl.prototype.onValidateSuccess_ = function(mdToast, state, student) {
  this.Api_.Students.post(student, this.onAddSuccess_.bind(this, mdToast, state),
      this.onAddFailure_.bind(this));
};

AddStudentCtrl.prototype.onValidateFailure_ = function(notPresentField) {
  alert(notPresentField + ' is required.');
};

AddStudentCtrl.prototype.onAddSuccess_ = function(mdToast, state, student) {
  mdToast.show(
    mdToast.simple()
      .content('Student successfully created.')
      .position('top right')
      .hideDelay(3000)
   );
  state.go('questionpaper', {
    studentId: student.id,
    questionPaperCode: student.question_paper_code
  });
};

AddStudentCtrl.prototype.onAddFailure_ = function() {
  
};

AddStudentCtrl.prototype.selectedSchoolNameItemChange_ = function(schoolNameItem) {
  console.log(schoolNameItem);
};

AddStudentCtrl.prototype.searchSchoolNameChange_ = function(schoolName) {
  console.log('schoolName: ' + schoolName);
};

AddStudentCtrl.prototype.querySearchSchoolName_ = function(scope, query) {
  if (!query) return [];
  var d = this.q_.defer();
  this.Api_.Schools.query({'q': query}, function(results) {
    d.resolve(results);
  }, function(error) {
    console.log(error);
    d.reject();
  });
  return d.promise;
};

AddStudentCtrl.prototype.selectedPlaceItemChange_ = function(schoolNameItem) {
  console.log(schoolNameItem);
};

AddStudentCtrl.prototype.searchPlaceChange_ = function(schoolName) {
  console.log('Place: ' + schoolName);
};

AddStudentCtrl.prototype.querySearchPlace_ = function(scope, query) {
  if (!query) return [];
  var d = this.q_.defer();
  this.Api_.Places.query({'q': query}, function(results) {
    d.resolve(results);
  }, function(error) {
    console.log(error);
    d.reject();
  });
  return d.promise;
};

angular.module('myApp.createstudent', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('createstudent', {
    url: '/createstudent',
    templateUrl: 'studentsview/createstudent.html',
    controller: 'AddStudentCtrl'
  });
}])
.controller('AddStudentCtrl', AddStudentCtrl);