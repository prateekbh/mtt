'use strict';

var QuestionPaperCtrl = function($q, $scope, $stateParams, $state, Api) {
  this.q_ = $q;
  this.Api_ = Api;
  this.studentId_ = $stateParams.studentId;
  this.questionPaperCode_ = $stateParams.questionPaperCode;

  $scope.submit = this.submitAnswers_.bind(this, $scope, $state);
  $scope.answers = {};

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
  scope.questionPaper = questionPaper;
  console.log(questionPaper);
};

QuestionPaperCtrl.prototype.onLoadFailure_ = function(scope) {
  // alert('Unable to load questionPaper.');
  this.onLoadSuccess_(scope);
};

QuestionPaperCtrl.prototype.submitAnswers_ = function(scope, state, answers) {
  console.log(answers);
  this.Api_.Answers.submit({
      student_id: this.studentId_,
      question_paper_code: this.questionPaperCode_,
      answers: answers
    }, this.onAnswersSubmitSuccess_.bind(this, scope, state),
    this.onAnswersSubmitFailure_.bind(this));
};

QuestionPaperCtrl.prototype.onAnswersSubmitSuccess_ = function(scope, state) {
  state.go('createstudent');
};

QuestionPaperCtrl.prototype.onAnswersSubmitFailure_ = function() {
  alert('Please submit the answers again.');
};

angular.module('myApp.questionpaperview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('questionpaper', {
    url: '/student/:studentId/questionpaper/:questionPaperCode',
    templateUrl: 'questionpaperview/questionpaperview.html',
    controller: 'QuestionPaperCtrl'
  });
}])
.controller('QuestionPaperCtrl', QuestionPaperCtrl);
