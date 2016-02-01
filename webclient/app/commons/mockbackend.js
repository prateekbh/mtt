angular.module('myApp.mockbackend', ['ngMockE2E'])
.run(function($httpBackend) {

  var students = [{
    id: 1,
    name: 'Chaitanya',
    place: 'Jettipalem',
    school_name: 'YRS High School',
    school_id: 100,
    test_center_name: 'Viveka Public School',
    test_center_id: 200,
    question_paper_code: 2334,
    mobile: 9700123456
  }];

  var questionpaper = {
    question_paper_code: 2334,
    questions: [
      'What is your palace?',
      'Where are your parents?',
      'How do you reach home?'
    ]
  };

  $httpBackend.whenPOST('www.mtt.com/signup').respond(200);

  $httpBackend.whenPOST('www.mtt.com/login').respond({
    id: 1,
    name: 'Chaitanya'
  });

  $httpBackend.whenPOST('www.mtt.com/answers').respond(200);


  $httpBackend.whenGET('www.mtt.com/questionpapers/2334').respond(questionpaper);

  $httpBackend.whenGET('www.mtt.com/students/1').respond(students[0]);

  $httpBackend.whenGET('www.mtt.com/students').respond(students);

  $httpBackend.whenPOST('www.mtt.com/students').respond(200);

  $httpBackend.whenGET(/.*/).passThrough();
});
