'use strict';

var AddStudentCtrl = function($scope, $mdDialog, $q, $mdToast, Api) {
  this.q_ = $q;
  this.Api_ = Api;
  this.mdDialog_ = $mdDialog;

  $scope.create = this.create_.bind(this, $mdToast);
  $scope.close = this.close_.bind(this);
  $scope.schools = [
    {name: 'School 1', id: 1},
    {name: 'School 2', id: 2},
    {name: 'School 3', id: 3},
  ];
  $scope.test_centers = [
    {name: 'Test Center 1', id: 1},
    {name: 'Test Center 2', id: 2},
    {name: 'Test Center 3', id: 3},
    {name: 'Test Center 4', id: 4},
  ];
}

AddStudentCtrl.prototype.create_ = function(mdToast, student) {
  this.validate_(student).then(this.onValidateSuccess_.bind(
        this, mdToast, student), this.onValidateFailure_.bind(this));
};

AddStudentCtrl.prototype.validate_ = function(c) {
  var d = this.q_.defer();
  var compulsoryFields = ['name', 'school_id', 'test_center_id', 'question_paper_code']
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

AddStudentCtrl.prototype.onValidateSuccess_ = function(mdToast, student) {
  this.Api_.Students.post(student, this.onAddSuccess_.bind(this, mdToast, student),
      this.onAddFailure_.bind(this));
};

AddStudentCtrl.prototype.onValidateFailure_ = function(notPresentField) {
  alert(notPresentField + ' is required.');
};

AddStudentCtrl.prototype.onAddSuccess_ = function(mdToast, student) {
  mdToast.show(
    mdToast.simple()
      .content('Student successfully created.')
      .position('top right')
      .hideDelay(3000)
   );
  this.mdDialog_.hide(student);
};

AddStudentCtrl.prototype.onAddFailure_ = function() {
  
};

AddStudentCtrl.prototype.close_ = function() {
  this.mdDialog_.hide();
};

angular.module('myApp.studentsview').controller('AddStudentCtrl', AddStudentCtrl);
