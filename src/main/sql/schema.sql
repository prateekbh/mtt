drop table question, contest, student, volunteer, center, volunteer_auth_token;
create table question (
    id SERIAL PRIMARY KEY,
    short_desc varchar(20),
    answered_correct integer,
    answered_wrong integer,
    not_attempted integer,
    created_on timestamp without time zone,
    modified_on timestamp without time zone
);

create table contest (
    _year integer PRIMARY KEY,
    attended integer,
    boys integer,
    girls integer,
    govt_school integer,
    private_school integer,
    centers int[]
);

create table student (
    id SERIAL PRIMARY KEY,
    name varchar(50),
    govt_or_private boolean,
    school varchar(50),
    place varchar(50),
    mandal varchar(50),
    center integer,
    correctly_answered_questions int[],
    incorrectly_answered_questions int[],
    unanswered_questions int[],
    answered_correct integer,
    answered_wrong integer,
    not_attempted integer,
    score double precision
);

create table volunteer (
    id SERIAL PRIMARY KEY,
    name varchar(50),
    user_name varchar(30),
    password varchar(30),
    center integer,
    created_on timestamp without time zone,
    modified_on timestamp without time zone
);

create table center (
    id SERIAL PRIMARY KEY,
    attended integer,
    boys integer,
    girls integer,
    govt_school integer,
    private_school integer
);

create table volunteer_auth_token (
    user_name varchar(50) PRIMARY KEY,
    auth_token varchar(50),
    created_on timestamp without time zone
);