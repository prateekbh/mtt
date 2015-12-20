create table question (
    id integer PRIMARY KEY,
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
    id integer,
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
    id integer,
    name varchar(50),
    center integer
);

create table center (
    id integer,
    attended integer,
    boys integer,
    girls integer,
    govt_school integer,
    private_school integer
);