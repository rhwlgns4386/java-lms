create table course (
    id bigint generated by default as identity,
    title varchar(255) not null,
    creator_id bigint not null,
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id)
);

create table ns_user (
    id bigint generated by default as identity,
    user_id varchar(20) not null,
    password varchar(20) not null,
    name varchar(20) not null,
    email varchar(50),
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id)
);

create table question (
    id bigint generated by default as identity,
    created_at timestamp not null,
    updated_at timestamp,
    contents clob,
    deleted boolean not null,
    title varchar(100) not null,
    writer_id bigint,
    primary key (id)
);

create table answer (
    id bigint generated by default as identity,
    created_at timestamp not null,
    updated_at timestamp,
    contents clob,
    deleted boolean not null,
    question_id bigint,
    writer_id bigint,
    primary key (id)
);

create table delete_history (
    id bigint not null,
    content_id bigint,
    content_type varchar(255),
    created_date timestamp,
    deleted_by_id bigint,
    primary key (id)
);

create table application_detail(
    id bigint generated by default as identity,
    session_id bigint,
    user_id bigint,
    status_code varchar(2) not null,
    payment_id varchar(50) ,
    created_at timestamp not null,
    updated_at timestamp,
    primary key(id)
);

create table ns_session (
    id bigint generated by default as identity,
    start_date varchar(8) not null,
    end_date varchar(8) not null,
    status_code varchar(2) ,
    progress_status_code varchar(2) not null ,
    recruitment_status_code varchar(2) not null,
    type_code varchar(2) not null,
    maximum_number_of_applicants bigint,
    fee_amount bigint,
    course_id bigint,
    created_at timestamp not null,
    updated_at timestamp,
    primary key(id)
);

create table session_image (
    id bigint generated by default as identity,
    url varchar(4000) not null ,
    size_of_image bigint,
    width bigint,
    height bigint,
    type_of_image varchar(8) not null,
    session_id bigint ,
    created_at timestamp not null,
    updated_at timestamp,
    primary key(id)
);
