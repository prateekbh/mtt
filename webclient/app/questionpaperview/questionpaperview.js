'use strict';

var QuestionPaperCtrl = function($q, $scope, $stateParams, $state, Api) {
  this.q_ = $q;
  this.Api_ = Api;

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

QuestionPaperCtrl.prototype.submitAnswers_ = function(scope, state, qpaper) {
  console.log(answers);
  this.Api_.Answers.submit(qpaper,
    this.onAnswersSubmitSuccess_.bind(this, scope, state),
    this.onAnswersSubmitFailure_.bind(this));
};

QuestionPaperCtrl.prototype.onAnswersSubmitSuccess_ = function(scope, state) {
  state.go('home');
};

QuestionPaperCtrl.prototype.onAnswersSubmitFailure_ = function() {
  alert('Please submit the answers again.');
};

angular.module('myApp.questionpaperview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('questionpaper', {
    url: '/questionpaper',
    templateUrl: 'questionpaperview/questionpaperview.html',
    controller: 'QuestionPaperCtrl'
  });
}])
.controller('QuestionPaperCtrl', QuestionPaperCtrl);
