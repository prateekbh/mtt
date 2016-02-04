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

  $httpBackend.whenPOST('http://www.mtt.com/signup').respond(200);

  $httpBackend.whenPOST('http://www.mtt.com/login').respond({
    id: 1,
    name: 'Chaitanya'
  });

  $httpBackend.whenPOST('http://www.mtt.com/answers').respond(200);


  $httpBackend.whenGET('http://www.mtt.com/questionpapers/234').respond(questionpaper);

  $httpBackend.whenPOST('http://www.mtt.com/students').respond({
    studentId: 1,
    questionPaperCode: 2
  });

  $httpBackend.whenGET('http://www.mtt.com/schools?q=H').respond([
    'YRS High School',
    'Vignan Residential High School',
    'ZPH School',
    'Bhashyam Public School',
    'Nalanda High School'
  ]);

  $httpBackend.whenGET('http://www.mtt.com/places?q=H').respond([
    'Hyderabad',
    'Delhi',
    'Mumbai',
    'Kolkata'
  ]);

  $httpBackend.whenGET(/.*/).passThrough();
});
