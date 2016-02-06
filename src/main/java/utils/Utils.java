package utils;

import servlet.Sets;
import servlet.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static int getMaxOfTable(String tableName, String columnName) throws SQLException {
        String getMaxQuery = "select max(" + columnName + ") from " + tableName;
//        System.out.println("getMaxQuery: " + getMaxQuery);
        Statement getMaxQueryStatement = Resources.connection.createStatement();
        ResultSet resultSet = getMaxQueryStatement.executeQuery(getMaxQuery);
        if (resultSet.next()) {
//            System.out.println("Returning max: " + resultSet.getInt("max"));
            return resultSet.getInt("max");
        }
//        System.out.println("Returning max -- default 1");
        return 1;   // for the first entry
    }

    public static boolean isValidAuthToken(String authToken) throws SQLException {
        if (null == authToken) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "authToken is null.\n");
            return false;
        }
//        Logger.getAnonymousLogger().log(Level.INFO, "authToken for validation : " + authToken);
        String getTokenDetailsQuery = String.format(MTT_CONSTANTS.GET_TOKEN_DETAILS_DB_QUERY, authToken);
//        Logger.getAnonymousLogger().log(Level.INFO, "getTokenQuery : " + getTokenDetailsQuery);
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(getTokenDetailsQuery);
        long tokenTimeStamp = 0;
        if (resultSet.next()) {
            tokenTimeStamp = resultSet.getDate(MTT_CONSTANTS.VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_CREATED_ON).getTime();
        }
//        Logger.getAnonymousLogger().log(Level.INFO, "current millis " + System.currentTimeMillis() + " tokenTimeStamp "
//                + tokenTimeStamp + " expiry time " + MTT_CONSTANTS.TOKEN_EXPIRY_TIME + " final result " + (System.currentTimeMillis() < tokenTimeStamp + MTT_CONSTANTS.TOKEN_EXPIRY_TIME));
        return System.currentTimeMillis() < tokenTimeStamp + MTT_CONSTANTS.TOKEN_EXPIRY_TIME;
    }

    public static String renewAuthTokenForUser(String uname) throws Exception {
        String deleteTokenQuery = String.format(MTT_CONSTANTS.DELETE_AUTH_TOKEN_DB_QUERY, uname);
        Statement statement = Resources.connection.createStatement();
        statement.execute(deleteTokenQuery);

        String token = UUID.randomUUID().toString();
        String writeTokenQuery = String.format(MTT_CONSTANTS.WRITE_AUTH_TOKEN_DB_QUERY, uname, token);
        Statement writeStatement = Resources.connection.createStatement();
        writeStatement.execute(writeTokenQuery);
        return token;
    }

    public static String getAuthToken(String cookie) {
        Logger.getAnonymousLogger().log(Level.INFO, " cookie : " + cookie);
        String[] nameValuePairs = cookie.split(MTT_CONSTANTS.COOKIE_SEPARATOR);
        for (String pair : nameValuePairs) {
            String[] nameValue = pair.split(MTT_CONSTANTS.NAME_VALUE_SEPARATOR, 2);
            if (MTT_CONSTANTS.AUTH_TOKEN_COOKIE_NAME.equals(nameValue[0])) {
                return nameValue[1];
            }
        }
        return "";
    }

    public static int getPaperCodeForStudent(int studentId) throws Exception {
        String getStudentDetailsQuery = String.format(MTT_CONSTANTS.GET_STUDENT_RECORD_QUERY, studentId);
//        System.out.println("getStudentDetailsQuery : " + getStudentDetailsQuery);
        Statement statement = Resources.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getStudentDetailsQuery);
        int paperCode = -1;
        if (resultSet.next()) {
            paperCode = resultSet.getInt(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_QUESTION_PAPER_CODE);
        }
//        System.out.println("studentId: " + studentId + " paperCode: " + paperCode);
        return paperCode;
    }

    public static ArrayList<String> getPlacesWithPrefix(String prefix) throws SQLException {
//        System.out.println("getPlaces: " + MTT_CONSTANTS.GET_PLACES_QUERY);
        String getPlaceQuery = String.format(MTT_CONSTANTS.GET_PLACES_QUERY, prefix, prefix);
//        System.out.println("getPlacesQuery: " + getPlaceQuery);

        Statement statement = Resources.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getPlaceQuery);
        ArrayList<String> result = new ArrayList<String>();
        int i = 0;
        while (resultSet.next()) {
            String place = resultSet.getString("name");
            String mandal = resultSet.getString("mandal");
            result.add(place + ", " + mandal);
        }
//        System.out.println("place result: " + result);
        return result;
    }

    public static ArrayList<String> getSchoolNamesWithPrefix(String prefix) throws SQLException {
        String getSchoolsQuery = String.format(MTT_CONSTANTS.GET_SCHOOLS_QUERY, prefix, prefix, prefix);
//        System.out.println("getSchoolsQuery: " + getSchoolsQuery);

        Statement statement = Resources.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getSchoolsQuery);
        ArrayList<String> result = new ArrayList<String>();
        int i = 0;
        while (resultSet.next()) {
            String schoolName = resultSet.getString("name");
            String village = resultSet.getString("village");
            String mandal = resultSet.getString("mandal");
            result.add(schoolName + ", " + village + ", " + mandal);
        }
//        System.out.println("School result: " + result);
        return result;
    }

    public static String getSchoolIdForName(String name) {
        return "1";
    }

    public static String getCenterIdForName(String name) {
        return "1";
    }

    public static final String[] getQuestionsForSet0() {
        // TODO
        String[] set0 = new String[] {
                " 1 1.2 + 2.3 + 3.4 + 4.5 ?",
                " 2 f(n)g(n) find g(50) ?",
                " 3 Wrong question..",
                " 4 Lata was ?",
                " 5 Ram Sam 20% discount ?",
                " 6 How many numbers under 1000 divisible 8 or 6?",
                " 7 p and q ... p/q + q/p = ?",
                " 8 5 men 24 days, 2 women 60 days?",
                " 9 fast runner, slow walker ?",
                " 10 ABC right angle triangle "
        };
        return set0;
    }

    public static String[] getAnswersForSet0() throws Exception {
        String getAnswersQuery = String.format(MTT_CONSTANTS.GET_ANSWERS_QUERY);
        System.out.println("getStudentDetailsQuery : " + getAnswersQuery);
        Statement statement = Resources.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getAnswersQuery);
        String[] result = new String[MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016];
        while (resultSet.next()) {
            int serial_number = resultSet.getInt(MTT_CONSTANTS.ANSWERS_TABLE_COLUMN_SERIAL_NUMBER);
            String answer = resultSet.getString(MTT_CONSTANTS.ANSWERS_TABLE_COLUMN_ANSWER);
            System.out.println("num " + serial_number + " answer: " + answer);
            result[serial_number - 1] = answer;
        }
        return result;
    }

    public static HashMap<Integer, String> getAnswers() throws Exception{
        String getAnswerSheetQuery = String.format(MTT_CONSTANTS.GET_ANSWER_SHEET_QUERY);
        System.out.println("getAnsSheetQuery : " + getAnswerSheetQuery);
        Statement statement = Resources.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(getAnswerSheetQuery);
        HashMap<Integer, String> summary = new HashMap<Integer, String>();
        while (resultSet.next()) {
            Integer studentId = resultSet.getInt("student_id");
            String answers = resultSet.getString("set0_equivalent_answers");
            summary.put(studentId, answers);
        }
        return summary;
    }

    public static ArrayList<Student> getAllStudents() throws Exception {
        return getStudentsByQuery(MTT_CONSTANTS.GET_ALL_STUDENTS_QUERY);
    }

    public static ArrayList<Student> getGovtStudents() throws Exception {
        String query = "select * from student where school in (select name from school where is_govt = true)";
        return getStudentsByQuery(query);
    }

    public static ArrayList<Student> getGovtGirlStudents() throws Exception {
        String query = "select * from student where school in (select name from school where is_govt = true)" +
                "and sex = 'Female'";
        return getStudentsByQuery(query);
    }

    public static ArrayList<Student> getStudentsByQuery(String getStudentsQuery) throws Exception {
        Statement statement = Resources.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(getStudentsQuery);
        ArrayList<Student> students = new ArrayList<Student>();
        while (resultSet.next()) {
            Student student = new Student(
                    resultSet.getInt(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_ID),
                    resultSet.getString(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_NAME),
                    resultSet.getInt(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_QUESTION_PAPER_CODE),
                    resultSet.getString(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_SCHOOL),
                    resultSet.getString(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_PLACE),
                    resultSet.getString(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_CENTER),
                    resultSet.getString(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_GENDER),
                    0.0         // temp value
            );
            students.add(student);
        }
        return students;
    }

    public static void populateQuestionStats() throws Exception {

        // index
        int[] correct = new int[MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016];
        int[] wrong = new int[MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016];
        int[] unanswered = new int[MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016];
        HashMap<Integer, String> summary = getAnswers();
        for (String answer : summary.values()) {
//            System.out.println("answer: " + answer);
            for (int i = 0; i < MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016; i++) {
                char ch = answer.charAt(i);
                if (ch == 'C') {
                    correct[i]++;
                } else if (ch == 'W') {
                    wrong[i]++;
                } else if (ch == 'U') {
                    unanswered[i]++;
                } else {
                    System.out.println("SOMETHING WENT WRONG ... ILLEGAL CHAR as ANSWER : " + ch);
                    throw new RuntimeException("ch : " + ch);
                }
            }
        }

        // compute effective scores
        double score[] = new double[MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016];
        for (int i = 0; i < MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016; i++) {
            score[i] = effectiveScore(correct[i], wrong[i], unanswered[i]);
        }

        System.out.println("Computed: ");
        for (int i = 0; i < MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016; i++) {
            System.out.println("Question " + (i + 1) + " Correct: " + correct[i] + " Wrong: " + wrong[i] +
                    " Unanswered: " + unanswered[i] + " score " + score[i]);
        }

        System.out.println("********** computing student stats");

        // compute individual ranks

        // preprocessing
        ArrayList<Student> allStudents = getAllStudents();
        System.out.println("total students: " + allStudents.size());
        for (Student student : allStudents) {
            String correctedAnswers = summary.get(student.getId());
            if (null == correctedAnswers) {
                // todo:
//                throw new RuntimeException("This student does not have answers entry." + student);
                System.out.println("does not have answers entry: " + student);
                continue;
            }
            double studentScore = 0.0;
            for (int i = 0; i < MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016; i++) {
                char ch = correctedAnswers.charAt(i);
                if (ch == 'C') {
                    studentScore += correct[i];
                } else if (ch == 'W') {
//                    studentScore -= negative[i];
                } else if (ch != 'U') {
                    throw new Exception("Illegal response in answer sheet.");
                }
            }
            student.setScore(studentScore);
//            System.out.println("Student result: " + student);
        }

        // Open top 25
//        ArrayList<Student> openTop25 = getTopNStudents(allStudents, 25);

        ArrayList<Student> sorted = sort(allStudents);

        System.out.println("********************** ################################## ******************************");
        int k = 0;
        for (Student st : sorted) {
            if (k > 50) break;
            k++;
            System.out.println(st);
        }


        // Govt Open

        // Govt Girls



        // compute school ranks

        // Open

        // Govt

        return ;
    }

    public static ArrayList<Student> sort(ArrayList<Student> all) {
        ArrayList<Student> result = new ArrayList<Student>(all.size());
        for (int i = 0; i < all.size(); i++) {
            for (int j = i + 1; j < all.size(); j++) {
                Student target = all.get(i);
                Student source = all.get(j);
                if (target.getScore() < source.getScore()) {
                    all.set(i, source);
                    all.set(j, target);
                }
            }
        }
        return all;
    }

    public static ArrayList<Student> getTopNStudents(ArrayList<Student> studentsList, int number) {
        ArrayList<Student> result = new ArrayList<Student>(number);
        double leastScoreInToppers = 0.0;
        Set<Integer> visited = new HashSet<Integer>();
        for (int i = 0; i < number; i++) {
            double max = -1;
            Student maxStudent = null;
            for (Student student : studentsList) {
                if (visited.contains(Integer.valueOf(student.getId()))) continue;
                if (max <= student.getScore()) {
                    max = student.getScore();
                    maxStudent = student;
                }
            }
            result.add(maxStudent);
        }
        return result;
    }

    public static double getMin(ArrayList<Student> list) {
        double min = 10000.0;
        for (int i = 0; i <list.size(); i++) {
            min = Math.min(min, list.get(i).getScore());
        }
        return min;
    }

    public static String convertToSet0Answers(String answers, int code) {
        char[] set0Answers = new char[MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016];
        ArrayList<Integer> order = Sets.getOrderForCode(code);

        for (int i = 0; i < MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016; i++) {
            set0Answers[order.get(i) - 1] = answers.charAt(i);
        }
        return new String(set0Answers);
    }

    public static double effectiveScore(int correctly_answered, int wrongly_answered, int unanswered) {
        return MTT_CONSTANTS.MIN_SCORE + MTT_CONSTANTS.WEIGHT_FACTOR *
                (1 - (double) correctly_answered / (double) (wrongly_answered + unanswered + correctly_answered)); // 1 + 4 * (1 - solved/unsolved)
    }

//    public static double negativeScore(int corrects, int wrongs, int unanswered) {
//        double result =   ((double)corrects)/wrongs;
//        result = Math.max(0.1, result);     // at least 0.1
//        return Math.min(2.0, result);       // at most 2.0
//    }
}
