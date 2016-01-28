package servlet;

public interface ResourcesPath {
  String LOGIN = "login";
  String SIGNUP = "signup";
  String CUSTOMER = "customers/{trNumber}";
  String VOLUNTEERS = "volunteers";
  String VOLUNTEER = "volunteers/{volunteerId}";
  String STUDENTS = "students";
  String STUDENT = "students/{studentId}";
  String ANSWER_SHEETS = "answerSheets";
  String ANSWER_SHEET = "answerSheet/{studentId}"; // answer sheet of the given student
  String QUESTION_PAPERS = "questionPapers";
  String QUESTION_PAPER = "questionPaper/{questionPaperCode}";
}
