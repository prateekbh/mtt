'use strict';

var QuestionPaperCtrl = function($q, $scope, $stateParams, $state, $mdDialog, Api) {
  this.q_ = $q;
  this.Api_ = Api;

  this.studentId_ = $stateParams.studentId;
  this.questionPaperCode_ = $stateParams.questionPaperCode;

  $scope.submit = this.submitAnswers_.bind(this, $scope, $state);
  $scope.answers = {};
  $scope.questions = [];

  this.refresh_($scope);
}

QuestionPaperCtrl.prototype.refresh_ = function(scope) {
  this.load_(scope);
};

QuestionPaperCtrl.prototype.load_ = function(scope) {
  this.Api_.QuestionPapers.get({questionPaperCode: this.questionPaperCode_},
      this.onLoadSuccess_.bind(this, scope),
      this.onLoadFailure_.bind(this));
};

QuestionPaperCtrl.prototype.onLoadSuccess_ = function(scope, questionPaper) {
  scope.questions = questionPaper.questions;
  console.log(questionPaper);
};

QuestionPaperCtrl.prototype.onLoadFailure_ = function(scope) {
  // alert('Unable to load questionPaper.');
  this.onLoadSuccess_(scope);
};

QuestionPaperCtrl.prototype.submitAnswers_ = function(scope, state, answers) {
  console.log("gonna submit answers");
  console.log(answers);
  var qpaper = {};
  qpaper.studentId = this.studetnId_;
  qpaper.questionPaperCode = this.questionPaperCode_;
  qpaper.answers = answers;
  this.Api_.Answers.submit(qpaper,
    this.onAnswersSubmitSuccess_.bind(this, scope, state),
    this.onAnswersSubmitFailure_.bind(this));
};

QuestionPaperCtrl.prototype.onAnswersSubmitSuccess_ = function(scope, state) {
  state.go('studentinfo');
};

QuestionPaperCtrl.prototype.onAnswersSubmitFailure_ = function() {
  alert('Please submit the answers again.');
};

angular.module('myApp.questionpaperview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('questionpaper', {
    url: 'students/:studentId/questionpaper/:questionPaperCode',
    templateUrl: 'questionpaperview/questionpaperview.html',
    controller: 'QuestionPaperCtrl'
  });
}])
.controller('QuestionPaperCtrl', QuestionPaperCtrl);
