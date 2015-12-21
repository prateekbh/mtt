package utils;


public class MTT_CONSTANTS {

    public static final String DB_FIELD_SEPERATOR = ", ";

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
    public static final String QUESTION_TABLE_COLUMN__ID = "id";
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
    public static final String STUDENT_TABLE_COLUMN_GOVT_OR_PVT = "govt_or_private";
    public static final String STUDENT_TABLE_COLUMN_SCHOOL = "school";
    public static final String STUDENT_TABLE_COLUMN_PLACE = "place";
    public static final String STUDENT_TABLE_COLUMN_MANDAL = "mandal";
    public static final String STUDENT_TABLE_COLUMN_CENTER = "center";
    public static final String STUDENT_TABLE_COLUMN_CORRECTLY_ANSWERED_QUESTIONS = "correctly_answered_questions";
    public static final String STUDENT_TABLE_COLUMN_INCORRECTLY_ANSWERED_QUESTIONS = "incorrectly_answered_questions";
    public static final String STUDENT_TABLE_COLUMN_UNANSWERED_QUESTIONS = "unanswered_questions";
    public static final String STUDENT_TABLE_COLUMN_SCORE = "score";


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



    public static final String TABLE_NAME_VOLUNTEER_AUTH_TOKEN = "volunteer_auth_token";
    public static final String VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_USER_NAME = "user_name";
    public static final String VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_AUTH_TOKEN = "auth_token";
    public static final String VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_CREATED_ON = "created_on";

    // request param names
    public static final String VOLUNTEER_NAME_REQUEST_PARAM = "vname";
    public static final String VOLUNTEER_USER_NAME_REQUEST_PARAM = "vuname";
    public static final String VOLUNTEER_PASSWORD_REQUEST_PARAM = "vpwd";

    public static final String AGENT_TABLE = "agent";
    public static final String USER_NAME_COLUMN_DB_AGENT_TABLE = "user_name";
    public static final String AGENT_NAME_COLUMN_DB_AGENT_TABLE = "agent_name";
    public static final String PASSWORD_HASH_COLUMN_DB_AGENT_TABLE = "password_hash";
    public static final String CREATED_ON_COLUMN_DB_AGENT_TABLE = "created_on";
    public static final String MODIFIED_ON_COLUMN_DB_AGENT_TABLE = "modified_on";

    public static final String AGENT_AUTH_TOKEN_TABLE = "agent_token";
    public static final String USER_NAME_COLUMN_DB_AGENT_TOKEN_TABLE = "user_name";
    public static final String TOKEN_COLUMN_DB_AGENT_TOKEN_TABLE = "auth_token";
    public static final String TOKEN_CREATED_ON_COLUMN_DB_AGENT_TOKEN_TABLE = "created_on";

    public static final String CUSTOMER_TABLE = "customer";
    public static final String CUSTOMER_NAME_COLUMN_DB_CUSTOMER_TABLE = "name";
    public static final String TR_NUMBER_COLUMN_DB_CUSTOMER_TABLE = "tr_number";
    public static final String MOBILE_NUMBER_COLUMN_DB_CUSTOMER_TABLE = "mobile_number";
    public static final String VEHICLE_COLUMN_DB_CUSTOMER_TABLE = "vehicle";
    public static final String AGENT_NAME_COLUMN_DB_CUSTOMER_TABLE = "agent_name";
    public static final String STATUS_COLUMN_DB_CUSTOMER_TABLE = "status";
    public static final String CREATED_ON_COLUMN_DB_CUSTOMER_TABLE = "created_on";
    public static final String MODIFIED_ON_COLUMN_DB_CUSTOMER_TABLE = "modified_on";



    public static final long TOKEN_EXPIRY_TIME = 30L * 24 * 60 * 60 * 1000L;  // 30 days * 24 hrs * 60 min * 60 sec * 1000 millis

    public static final String USER_NAME_REQUEST_PARAM = "user_name";
    public static final String PASSWORD_REQUEST_PARAM = "password";
    public static final String AGENT_NAME_REQUEST_PARAM = "agent_name";
    public static final String AUTH_TOKEN_COOKIE_NAME = "auth_token";
    public static final long AGENT_REGISTRATION_OTP_VALID_FOR = (5 * 60 * 1000);   // 5 minutes -> millis
    public static final String AGENT_PASSWORD_ENCODING = "UTF-8";

    //Insert customer params
    public static final String NAME_PUT_CUSTOMER_PARAM = "name";
    public static final String TR_NUMBER_PUT_CUSTOMER_PARAM = "trNumber";
    public static final String MOBILE_NUMBER_PUT_CUSTOMER_PARAM = "mobile";
    public static final String VEHICLE_PUT_CUSTOMER_PARAM = "vehicle";
    public static final String AGENT_NAME_PUT_CUSTOMER_PARAM = "agentname";

//    public static final String WRITE_AUTH_TOKEN_DB_QUERY = "insert into " + AGENT_AUTH_TOKEN_TABLE + "(" + USER_NAME_COLUMN_DB_AGENT_TOKEN_TABLE + ", " +
//            TOKEN_COLUMN_DB_AGENT_TOKEN_TABLE + ", " + TOKEN_CREATED_ON_COLUMN_DB_AGENT_TOKEN_TABLE + ") values('%s', '%s', now())";
//    public static final String DELETE_AUTH_TOKEN_DB_QUERY = "delete from " + AGENT_AUTH_TOKEN_TABLE + " where " + USER_NAME_COLUMN_DB_AGENT_TOKEN_TABLE + "='%s'";
    public static final String GET_UNAME_PWD_DB_QUERY =
            "select " + PASSWORD_HASH_COLUMN_DB_AGENT_TABLE + " from " + AGENT_TABLE + " where " + USER_NAME_COLUMN_DB_AGENT_TABLE + "='%s'";

    public static final String GET_TOKEN_DETAILS_DB_QUERY = "select " + TOKEN_CREATED_ON_COLUMN_DB_AGENT_TOKEN_TABLE + " from " +
            AGENT_AUTH_TOKEN_TABLE + " where " + TOKEN_COLUMN_DB_AGENT_TOKEN_TABLE + "='%s'";
    public static final String GET_USERS_FOR_AGENT_QUERY = "select name, tr_number, mobile_number, vehicle, " +
            "agent_name, status, created_on, modified_on from customer where agent_name = '%s'";
    public static final String GET_ALL_CUSTOMERS_QUERY = "select name, tr_number, mobile_number, vehicle, " +
            "agent_name, status, created_on, modified_on from customer";


    /*
    For MTT *******************
     */
    public static final String VOLUNTEER_EXISTENCE_ENQURY_DB_QUERY =
            "select " + VOLUNTEER_TABLE_COLUMN_USER_NAME + " from " + TABLE_NAME_VOLUNTEER + " where " +
                    VOLUNTEER_TABLE_COLUMN_USER_NAME + "='%s'";

    public static final String INSERT_VOLUNTEER_DB_QUERY = "insert into " + TABLE_NAME_VOLUNTEER +
            "(" + VOLUNTEER_TABLE_COLUMN_NAME + ", " + VOLUNTEER_TABLE_COLUMN_USER_NAME + ", " +
            VOLUNTEER_TABLE_COLUMN_PASSWORD + ", " + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_CREATED_ON + ", " +
            VOLUNTEER_TABLE_COLUMN_MODIFIED_ON + ") values('%s', '%s', '%s', now(), now())";

    public static final String WRITE_AUTH_TOKEN_DB_QUERY = "insert into " + TABLE_NAME_VOLUNTEER_AUTH_TOKEN +
            "(" + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_USER_NAME + ", " + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_AUTH_TOKEN +
            ", " + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_CREATED_ON + ") values('%s', '%s', now())";


    public static final String DELETE_AUTH_TOKEN_DB_QUERY = "delete from " + TABLE_NAME_VOLUNTEER_AUTH_TOKEN +
            " where " + VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_USER_NAME + "='%s'";


//    public static final String GET_AGENT_NAME_FOR_TOKEN_QUERY = "select "
//            + USER_NAME_COLUMN_DB_AGENT_TABLE + DB_FIELD_SEPERATOR
//            + AGENT_NAME_COLUMN_DB_AGENT_TABLE + DB_FIELD_SEPERATOR
//            + CREATED_ON_COLUMN_DB_AGENT_TABLE + DB_FIELD_SEPERATOR
//            + MODIFIED_ON_COLUMN_DB_AGENT_TABLE
//            + " from " + AGENT_TABLE + " where " + USER_NAME_COLUMN_DB_AGENT_TABLE +
//            " = (select " + USER_NAME_COLUMN_DB_AGENT_TOKEN_TABLE + " from " + AGENT_AUTH_TOKEN_TABLE + " where "
//            + TOKEN_COLUMN_DB_AGENT_TOKEN_TABLE + " = '%s')";

    public static final String GET_AGENT_NAME_FOR_TOKEN_QUERY = "select "
            + USER_NAME_COLUMN_DB_AGENT_TOKEN_TABLE
            + " from " + AGENT_AUTH_TOKEN_TABLE + " where " + TOKEN_COLUMN_DB_AGENT_TOKEN_TABLE + " = '%s'";

    public static final String INSERT_CUSTOMER_QUERY = "insert into " + CUSTOMER_TABLE + "("
            + CUSTOMER_NAME_COLUMN_DB_CUSTOMER_TABLE + DB_FIELD_SEPERATOR
            + TR_NUMBER_COLUMN_DB_CUSTOMER_TABLE + DB_FIELD_SEPERATOR
            + MOBILE_NUMBER_COLUMN_DB_CUSTOMER_TABLE + DB_FIELD_SEPERATOR
            + VEHICLE_COLUMN_DB_CUSTOMER_TABLE + DB_FIELD_SEPERATOR
            + AGENT_NAME_COLUMN_DB_CUSTOMER_TABLE + DB_FIELD_SEPERATOR
            + STATUS_COLUMN_DB_CUSTOMER_TABLE + DB_FIELD_SEPERATOR
            + CREATED_ON_COLUMN_DB_CUSTOMER_TABLE + DB_FIELD_SEPERATOR
            + MODIFIED_ON_COLUMN_DB_CUSTOMER_TABLE + ") values('%s', '%s', '%s', '%s', '%s', '%s', now(), now())";

    public static final String READ_CUSTOMER_DB_QUERY = "select * from " + CUSTOMER_TABLE + " where "
            + TR_NUMBER_COLUMN_DB_CUSTOMER_TABLE + " = '%s'";

    public static final int HTTP_UNAUTH_CODE = 401;
    public static final int HTTP_CONFLICT_CODE = 409;
    public static final int HTTP_OK_CODE = 200;
    public static final int HTTP_INTERNAL_SERVER_ERROR= 500;
    public static final String COOKIE_SEPARATOR = ";";      // not sure
    public static final String NAME_VALUE_SEPARATOR = "=";
    public static final String HTTP_COOKIE_HEADER_NAME = "cookie";
    public static final String ACTIVE_REGISTRATION_STATUS = "ACTIVE";

    //TSRTA form data : page https://aptransport.in/TGCFSTONLINE/Reports/VehicleRegistrationSearch.aspx
    public static final String TSRTA_URL = "https://aptransport.in/TGCFSTONLINE/Reports/VehicleRegistrationSearch.aspx";
    public static final String STATUS_ID_TSRTA_SITE = "ctl00_OnlineContent_tdStatus";
    public static final String TR_NUMBER_INPUT_FORM_ID = "T";
    public static final String NO_DATA_FOUND_TSRTA_SITE = "No Data Found";
    public static final String TR_NUMBER_RESPONSE_FORM_ID_TSRTA_SITE = "ctl00_OnlineContent_txtInput";
    public static final String GET_DATA_BUTTON_INPUT_FORM_TSRTA_SITE = "ctl00_OnlineContent_btnGetData";
    public static final String FORM_ID_TSRTA_SITE = "aspnetForm";
    public static final String REGISTRATION_NUMBER_RESPONSE_TSRTA_SITE = "ctl00_OnlineContent_tdRegnNo";
    public static final String MAKER_CLASS_RESPONSE_TSRTA_SITE = "ctl00_OnlineContent_tdMkrClass";
    public static final String VEHICLE_COLOR_RESPONSE_TSRTA_SITE = "ctl00_OnlineContent_tdColor";

    //phone information todo: read the values from config
    public static final String USER_EMAIL_SMS_API = "reddy6sigma@gmail.com";
    public static final String PASSWORD_SMS_API = "iloveiit";
    public static final String SENDER_ID_SMS_API = "TEST SMS";
    public static final String INFORM_CUSTOMER_SMS = "Dear %s, your vehicle %s with TR Number %s got registered with reg.no %s. " +
            "Please come to the RTO office on next working day around 10 AM to collect the documents. " +
            "Call %s at %s for more details.\nThank you.";

    public static final String SMS_API_URL = "http://api.mVaayoo.com/mvaayooapi/MessageCompose?user="+
            USER_EMAIL_SMS_API + ":" + PASSWORD_SMS_API + "&senderID=" + SENDER_ID_SMS_API +
            "&receipientno=%s&msgtxt=" + INFORM_CUSTOMER_SMS + "&state=4";
}
