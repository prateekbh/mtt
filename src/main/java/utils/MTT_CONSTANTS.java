package utils;


public class MTT_CONSTANTS {

    public static final double MIN_SCORE = 1.0;
    public static final double WEIGHT_FACTOR = 4.0;
    public static final int NUMBER_OF_QUESTIONS_IN_2016 = 10;
    public static final int NUMBER_OF_SETS = 10;
//    create table question (
//            id integer PRIMARY KEY,
//            short_desc varchar(20),
//    answered_correct integer,
//    answered_wrong integer,
//    not_attempted integer,
//    created_on timestamp without time zone,
//    modified_on timestamp without time zone
//    );

    public static final String TABLE_NAME_QUESTION = "question";
    public static final String QUESTION_TABLE_COLUMN__SERIAL_NUMBER = "serial_number";
    public static final String QUESTION_TABLE_COLUMN_SHORT_DESC = "short_desc";
    public static final String QUESTION_TABLE_COLUMN_ANSWERED_CORRECT = "answered_correct";
    public static final String QUESTION_TABLE_COLUMN_ANSWERED_WRONG = "answered_wrong";
    public static final String QUESTION_TABLE_COLUMN_NOT_ATTEMPTED = "not_attmepted";
    public static final String QUESTION_TABLE_COLUMN_CREATED_ON = "created_on";
    public static final String QUESTION_TABLE_COLUMN_MODIFIED_ON = "modified_on";

//    create table contest (
//            _year integer PRIMARY KEY,
//            attended integer,
//            boys integer,
//            girls integer,
//            govt_school integer,
//            private_school integer,
//            centers int[]
//    );
//
    public static final String TABLE_NAME_STUDENT = "student";
    public static final String STUDENT_TABLE_COLUMN_ID = "id";
    public static final String STUDENT_TABLE_COLUMN_NAME = "name";
    public static final String STUDENT_TABLE_COLUMN_QUESTION_PAPER_CODE = "question_paper_code";
    public static final String STUDENT_TABLE_COLUMN_SCHOOL = "school";
    public static final String STUDENT_TABLE_COLUMN_PLACE = "place";
    public static final String STUDENT_TABLE_COLUMN_CENTER = "center";
    public static final String STUDENT_TABLE_COLUMN_GENDER = "sex";
    public static final String STUDENT_TABLE_COLUMN_CREATED_ON = "created_on";
    public static final String STUDENT_TABLE_COLUMN_MODIFIED_ON = "modified_on";
//    public static final String STUDENT_TABLE_COLUMN_CORRECTLY_ANSWERED_QUESTIONS = "correctly_answered_questions";
//    public static final String STUDENT_TABLE_COLUMN_INCORRECTLY_ANSWERED_QUESTIONS = "incorrectly_answered_questions";
//    public static final String STUDENT_TABLE_COLUMN_UNANSWERED_QUESTIONS = "unanswered_questions";
//    public static final String STUDENT_TABLE_COLUMN_SCORE = "score";


    //    create table student (
//            id integer,
//            name varchar(50),
//    govt_or_private boolean,
//    school varchar(50),
//    place varchar(50),
//    mandal varchar(50),
//    center integer,
//    correctly_answered_questions int[],
//    incorrectly_answered_questions int[],
//    unanswered_questions int[],
//    answered_correct integer,
//    answered_wrong integer,
//    not_attempted integer,
//    score double precision
//    );
//
//    create table volunteer (
//            id integer,
//            name varchar(50),
//            user_name varchar(30),
//    center integer,
//    created_on timestamp without time zone,
//    modified_on timestamp without time zone
    //    );
//
    public static final String TABLE_NAME_VOLUNTEER = "volunteer";
    public static final String VOLUNTEER_TABLE_COLUMN_ID = "id";
    public static final String VOLUNTEER_TABLE_COLUMN_NAME = "name";
    public static final String VOLUNTEER_TABLE_COLUMN_USER_NAME = "user_name";
    public static final String VOLUNTEER_TABLE_COLUMN_PASSWORD = "password";
    public static final String VOLUNTEER_TABLE_COLUMN_CENTER = "center";
    public static final String VOLUNTEER_TABLE_COLUMN_CREATED_ON = "created_on";
    public static final String VOLUNTEER_TABLE_COLUMN_MODIFIED_ON = "modified_on";

    //    create table center (
//            id integer,
//            attended integer,
//            boys integer,
//            girls integer,
//            govt_school integer,
//            private_school integer
//    );
//


//    create table volunteer_auth_token (
//            user_name varchar(50) PRIMARY KEY,
//    auth_token varchar(50),
//    created_on timestamp without time zone
//    );
//
    public static final String TABLE_NAME_VOLUNTEER_AUTH_TOKEN = "volunteer_auth_token";
    public static final String VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_USER_NAME = "user_name";
    public static final String VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_AUTH_TOKEN = "auth_token";
    public static final String VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_CREATED_ON = "created_on";

    // answers
    public static final String TABLE_NAME_ANSWERS = "answers";
    public static final String ANSWERS_TABLE_COLUMN_ANSWER = "answer";
    public static final String ANSWERS_TABLE_COLUMN_SERIAL_NUMBER = "serial_number";

    public static final String TABLE_NAME_PLACE = "place";
    public static final String TABLE_NAME_SCHOOL = "school";
    public static final String TABLE_NAME_ANSWER_SHEET = "answer_sheet";
    // request param names

    // common request params
    public static final String CENTER_ID_REQUEST_PARAM = "centerId";

    public static final String VOLUNTEER_NAME_REQUEST_PARAM = "vname";
    public static final String VOLUNTEER_USER_NAME_REQUEST_PARAM = "vuname";
    public static final String VOLUNTEER_PASSWORD_REQUEST_PARAM = "vpwd";

    public static final String STUDENT_ID_REQUEST_PARAM = "studentId";
    public static final String STUDENT_NAME_REQUEST_PARAM = "studentName";
    public static final String SCHOOL_ID_REQUEST_PARAM = "schoolId";
    public static final String STUDENT_PLACE_REQUEST_PARAM = "studentPlace";
    public static final String QUESTION_PAPER_CODE_REQUEST_PARAM = "questionPaperCode";
    public static final String STUDENT_SEX_REQUEST_PARAM = "sex";

    public static final long TOKEN_EXPIRY_TIME = 30L * 24 * 60 * 60 * 1000L;  // 30 days * 24 hrs * 60 min * 60 sec * 1000 millis

    public static final String AUTH_TOKEN_COOKIE_NAME = "auth_token";

    public static final String GET_UNAME_PWD_DB_QUERY =
            "select " + VOLUNTEER_TABLE_COLUMN_PASSWORD + " from " + TABLE_NAME_VOLUNTEER + " where "
                    + VOLUNTEER_TABLE_COLUMN_USER_NAME + "='%s'";

    public static final String GET_TOKEN_DETAILS_DB_QUERY = "select " + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_CREATED_ON
            + " from " + TABLE_NAME_VOLUNTEER_AUTH_TOKEN + " where "
            + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_AUTH_TOKEN + "='%s'";

    /*
    For MTT *******************
     */
    public static final String VOLUNTEER_EXISTENCE_ENQURY_DB_QUERY =
            "select " + VOLUNTEER_TABLE_COLUMN_USER_NAME + " from " + TABLE_NAME_VOLUNTEER + " where lower(" +
                    VOLUNTEER_TABLE_COLUMN_USER_NAME + ")=lower('%s')";

    public static final String INSERT_VOLUNTEER_DB_QUERY = "insert into " + TABLE_NAME_VOLUNTEER +
            "(" + VOLUNTEER_TABLE_COLUMN_NAME + ", "
            + VOLUNTEER_TABLE_COLUMN_USER_NAME + ", "
            + VOLUNTEER_TABLE_COLUMN_PASSWORD + ", "
            + VOLUNTEER_TABLE_COLUMN_CREATED_ON + ", "
            + VOLUNTEER_TABLE_COLUMN_MODIFIED_ON
            + ") values('%s', '%s', '%s', now(), now())";

    public static final String WRITE_AUTH_TOKEN_DB_QUERY = "insert into " + TABLE_NAME_VOLUNTEER_AUTH_TOKEN +
            "(" + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_USER_NAME + ", " + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_AUTH_TOKEN +
            ", " + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_CREATED_ON + ") values('%s', '%s', now())";


    public static final String DELETE_AUTH_TOKEN_DB_QUERY = "delete from " + TABLE_NAME_VOLUNTEER_AUTH_TOKEN +
            " where " + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_USER_NAME + "='%s'";


    public static final String GET_VOLUNTEER_NAME_FOR_TOKEN_QUERY = "select "
            + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_USER_NAME + " from "
            + TABLE_NAME_VOLUNTEER_AUTH_TOKEN + " where " + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_AUTH_TOKEN + " = '%s'";

    public static final String INSERT_STUDENT_QUERY = "insert into " + TABLE_NAME_STUDENT + "("
            + STUDENT_TABLE_COLUMN_ID + ", "
            + STUDENT_TABLE_COLUMN_QUESTION_PAPER_CODE + ", "
            + STUDENT_TABLE_COLUMN_NAME + ", "
            + STUDENT_TABLE_COLUMN_SCHOOL + ", "
            + STUDENT_TABLE_COLUMN_PLACE + ", "
            + STUDENT_TABLE_COLUMN_CENTER + ", "
            + STUDENT_TABLE_COLUMN_GENDER + ", "
            + STUDENT_TABLE_COLUMN_CREATED_ON + ", "
            + STUDENT_TABLE_COLUMN_MODIFIED_ON + ") " +
            "values('%s', '%s', '%s', '%s', '%s', '%s', '%s', now(), now())" ;

    // TODO: clean this shit
    public static final String INSERT_ANSWERS_QUERY = "insert into answer_sheet(student_id, set_number, " +
            "answers, set0_equivalent_answers, created_on, modified_on) values(%s, %s, '%s', '%s', now(), now())";

    public static final String GET_STUDENT_RECORD_QUERY = "select * from student where " + STUDENT_TABLE_COLUMN_ID
            + " = %s";
    public static final String GET_ANSWERS_QUERY = "select * from " + TABLE_NAME_ANSWERS;

    public static final String GET_PLACES_QUERY = "select * from " + TABLE_NAME_PLACE +
            " where name ilike '%%%s%%' or mandal ilike '%%%s%%'";

    public static final String GET_SCHOOLS_QUERY = "select * from " + TABLE_NAME_SCHOOL +
            " where name ilike '%%%s%%' or village ilike '%%%s%%' or mandal ilike '%%%s%%'";

    public static final String GET_ANSWER_SHEET_QUERY = "select * from " + TABLE_NAME_ANSWER_SHEET;
    public static final String GET_ALL_STUDENTS_QUERY = "select * from " + TABLE_NAME_STUDENT;

    public static final String GET_STUDENT_FOR_ID_QUERY = "select * from " + TABLE_NAME_STUDENT + " where "
            + STUDENT_TABLE_COLUMN_ID + " = %s";

    public static final int HTTP_UNAUTH_CODE = 401;
    public static final int HTTP_NOT_FOUND_CODE = 404;
    public static final int HTTP_CONFLICT_CODE = 409;
    public static final int HTTP_OK_CODE = 200;
    public static final int HTTP_INTERNAL_SERVER_ERROR= 500;
    public static final String COOKIE_SEPARATOR = ";";      // not sure
    public static final String NAME_VALUE_SEPARATOR = "=";
    public static final String HTTP_COOKIE_HEADER_NAME = "cookie";
}
