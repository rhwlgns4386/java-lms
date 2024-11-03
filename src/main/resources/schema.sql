create table course
(
    id         bigint generated by default as identity,
    title      varchar(255) not null,
    creator_id bigint       not null,
    created_at timestamp    not null,
    updated_at timestamp,
    primary key (id)
);


create table course_session
(
    id                    bigint generated by default as identity,
    course_id             bigint      not null,
    session_status        varchar(20) not null,
    maximum_number_people bigint default 0,
    session_pay           bigint default 0,
    session_pay_type      varchar(10) not null,
    start_date            timestamp   not null,
    end_date              timestamp   not null,
    primary key (id),
    foreign key (course_id) references course (id)
);

create table session_cover_image
(
    id                 bigint generated by default as identity,
    session_id         bigint       not null,
    stored_file_name   varchar(255) not null,
    original_file_name varchar(255) not null,
    width              int          not null,
    height             int          not null,
    primary key (id),
    foreign key (session_id) references course_session (id)
);

create table ns_user
(
    id         bigint generated by default as identity,
    user_id    varchar(20) not null,
    password   varchar(20) not null,
    name       varchar(20) not null,
    email      varchar(50),
    created_at timestamp   not null,
    updated_at timestamp,
    primary key (id)
);

create table question
(
    id         bigint generated by default as identity,
    created_at timestamp    not null,
    updated_at timestamp,
    contents   clob,
    deleted    boolean      not null,
    title      varchar(100) not null,
    writer_id  bigint,
    primary key (id)
);

create table answer
(
    id          bigint generated by default as identity,
    created_at  timestamp not null,
    updated_at  timestamp,
    contents    clob,
    deleted     boolean   not null,
    question_id bigint,
    writer_id   bigint,
    primary key (id)
);

create table delete_history
(
    id            bigint not null,
    content_id    bigint,
    content_type  varchar(255),
    created_date  timestamp,
    deleted_by_id bigint,
    primary key (id)
);


create table session_student
(
    id         bigint generated by default as identity,
    session_id bigint not null,
    user_id    bigint not null,
    primary key (id),
    foreign key (session_id) references course_session (id),
    foreign key (user_id) references ns_user (id)
);
