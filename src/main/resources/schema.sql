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

CREATE TABLE session (
    session_id bigint generated by default as identity,
    course_id bigint not null,
    title varchar(255) not null,
    progress_status varchar(20),
    recruitment_status varchar(20),
    start_date datetime not null,
    end_date datetime not null,
    fee bigint not null,
    max_enrollments int,
    primary key (session_id)
);

CREATE TABLE enrollment (
    enrollment_id bigint generated by default as identity,
    session_id bigint not null,
    ns_user_id bigint not null,
    enrollment_status varchar(20) not null,
    enrolled_at timestamp default current_timestamp,
    primary key (enrollment_id),
    unique (session_id, ns_user_id)
);

CREATE TABLE cover_image (
    id bigint generated by default as identity,
    session_id bigint not null,
    file_name varchar(255) not null,
    image_size int not null,
    extension varchar(20) not null,
    width int not null,
    height int not null,
    primary key (id)
);