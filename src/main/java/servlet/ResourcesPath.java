package servlet;

public interface ResourcesPath {
  String LOGIN = "login";
  String SIGNUP = "signup";
  String STUDENTS = "students";
  String STUDENT = "students/{studentId}";
  String ANSWER_SHEETS = "answers";
  String ANSWER_SHEET = "answerSheet/{studentId}"; // answer sheet of the given student
  String QUESTION_PAPERS = "questionPapers";
  String QUESTION_PAPER = "questionPapers/{questionPaperCode}";
  String PLACES = "places";
  String SCHOOLS = "schools";
}
