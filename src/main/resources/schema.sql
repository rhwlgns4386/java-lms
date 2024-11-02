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

create table session_image (
    id bigint generated by default as identity,
    volume int,
    type varchar(20),
    width int,
    height int,
    primary key (id)
);

create table session
(
    id                 bigint generated by default as identity,
    session_start_date timestamp,
    session_end_date   timestamp,
    status             varchar(20),
    progress_status    varchar(20),
    recruiting_status    varchar(20),
    image_id           bigint,
    session_type       varchar(20),
    session_fee        int,
    max_student        int,
    primary key (id)
);

create table session_student(
    id bigint generated by default as identity,
    session_id bigint,
    user_id bigint,
    status varchar(20),
    primary key (id)
);

create table course_session(
   id bigint generated by default as identity,
   course_id bigint,
   session_id bigint,
   primary key (id)
);

create table session_session_image(
    id bigint generated by default as identity,
    session_id bigint,
    image_id bigint
)