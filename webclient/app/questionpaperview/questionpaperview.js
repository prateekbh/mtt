'use strict';

var QuestionPaperCtrl = function($q, $scope, $stateParams, $state, Api) {
  this.q_ = $q;
  this.Api_ = Api;
  this.questionPaperId_ = $stateParams.questionPaperId;

  $scope.submit = this.submitAnswers_.bind(this, $scope, $state);

  $scope.questionPaper = {
    question_paper_code: 12345,
    questions: [
      'What is your palace?',
      'Where are your parents?',
      'How do you reach home?'
    ]
  };
  $scope.answers = [];
  // this.refresh_($scope);
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
  questionPaper = {name: 'Hello'};
  scope.questionPaper = questionPaper;
  console.log(questionPaper);
};

QuestionPaperCtrl.prototype.onLoadFailure_ = function(scope) {
  // alert('Unable to load questionPaper.');
  this.onLoadSuccess_(scope);
}

QuestionPaperCtrl.prototype.submitAnswers_ = function(scope, state, answers) {
  console.log(answers);
} 

angular.module('myApp.questionpaperview', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('questionpaper', {
    url: '/questionpaper/:questionPaperId',
    templateUrl: 'questionpaperview/questionpaperview.html',
    controller: 'QuestionPaperCtrl'
  });
}])
.controller('QuestionPaperCtrl', QuestionPaperCtrl);
