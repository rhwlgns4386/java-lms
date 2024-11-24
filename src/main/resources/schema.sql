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

create table session (
    id bigint generated by default as identity,
    title varchar(255) not null,
    creator_id bigint not null,
    status VARCHAR(30) not null,
    enrollment_status VARCHAR(30) not null,
    price INT not null,
    pay_type VARCHAR(30) not null,
    max_student_count INT not null,
    cover_image_id bigint not null,
    start_date_time timestamp not null,
    end_date_time timestamp,
    primary key (id)
);

create table student (
    user_id bigint not null,
    session_id bigint not null,
    approval_status VARCHAR(30) default 'DISAPPROVED',
    UNIQUE (user_id, session_id)
);

create table cover_image (
    id bigint generated by default as identity,
    session_id bigint not null,
    image_type VARCHAR(30) not null,
    width INT not null,
    height INT not null,
    size bigint not null,
    primary key (id)
);
