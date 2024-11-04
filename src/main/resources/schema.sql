create table course
(
    id         bigint generated by default as identity,
    "order"    bigint,
    title      varchar(255) not null,
    creator_id bigint       not null,
    created_at timestamp    not null,
    updated_at timestamp,
    primary key (id)
);

create table session
(
    id                      bigint generated by default as identity,
    course_id               bigint       not null,
    title                   varchar(255) not null,
    start_at                timestamp    not null,
    end_at                  timestamp    not null,
    session_type            varchar(100) not null,
    session_status          varchar(100),
    session_progress_status varchar(100),
    session_recruit_status  varchar(100),
    capacity                integer,
    price                   bigint,
    created_at              timestamp    not null,
    updated_at              timestamp,
    primary key (id)
);

create table image
(
    id         bigint generated by default as identity,
    session_id bigint,
    size       integer,
    image_type varchar(10),
    width      integer,
    height     integer,
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id)
);

create table student
(
    ns_user_id bigint,
    session_id bigint,
    amount     bigint,
    status     varchar(10),
    primary key (ns_user_id)
);

create table lecturer
(
    ns_user_id bigint,
    session_id bigint,
    primary key (ns_user_id)
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
