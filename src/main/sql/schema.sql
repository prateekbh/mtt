--drop table question, contest, student, volunteer, center, volunteer_auth_token, school, config cascade;
drop table question, contest, student, center, school cascade;

create table if not exists question (
    id SERIAL PRIMARY KEY,
    short_desc varchar(20),
    answered_correct integer,
    answered_wrong integer,
    not_attempted integer,
    created_on timestamp without time zone,
    modified_on timestamp without time zone
);

create table if not exists answers (
    id SERIAL PRIMARY KEY,
    varchar(30) answer,
    created_on timestamp without time zone,
    modified_on timestamp without time zone
);

create table if not exists center (
    id SERIAL PRIMARY KEY,
    place varchar(50) not null,
    venue varchar(100) not null,
    attended integer,
    boys integer,
    girls integer,
    volunteers integer,
    govt_school integer,
    private_school integer
);

create table if not exists school (
    id SERIAL PRIMARY KEY,
    name varchar(100),
    village varchar(30),
    mandal varchar(30),
    is_govt boolean default true
);

create table if not exists contest (
    _year integer PRIMARY KEY,
    attended integer,
    boys integer,
    girls integer,
    govt_schools integer,
    private_schools integer,
    centers int[]
);

-- TODO: Restructure the tables.
create table if not exists student (
    id integer PRIMARY KEY,
    name varchar(50),
    question_paper_code integer not null,
    school integer REFERENCES school(id) not null,
    place varchar(50),
    center integer REFERENCES center(id) not null,
    sex varchar(10),
    created_on timestamp without time zone,
    modified_on timestamp without time zone
);

--     correctly_answered_questions int[],
--       incorrectly_answered_questions int[],
--       unanswered_questions int[],
--       answered_correct integer,
--       answered_wrong integer,
--       not_attempted integer,
--       score double precision

create table if not exists volunteer (
    id SERIAL PRIMARY KEY,
    name varchar(50),
    user_name varchar(30),
    password varchar(30),
    center integer REFERENCES center(id) not null,
    place varchar(50),
    created_on timestamp without time zone,
    modified_on timestamp without time zone
);

create table if not exists volunteer_auth_token (
    user_name varchar(50) PRIMARY KEY,
    auth_token varchar(50),
    created_on timestamp without time zone
);

create table if not exists config (
    lhs varchar(500) PRIMARY KEY,
    rhs varchar(500),
    created_on timestamp without time zone,
    modified_on timestamp without time zone
);

create table if not exists answer_sheet (
  studentId integer,
  answers varchar(100),
  created_on timestamp without time zone,
  modified_on timestamp without time zone
);