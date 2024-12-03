create table course
(
    id         bigint generated by default as identity,
    title      varchar(255) not null,
    creator_id bigint       not null,
    created_at timestamp    not null,
    updated_at timestamp,
    primary key (id)
);

create table session
(
    id              bigint generated by default as identity,
    charge          int         not null,
    capacity        int,
    sessionStatus   varchar(20) not null,
    image_file_name varchar(20) not null,
    image_width     int         not null,
    image_height    int         not null,
    image_size      int         not null,
    imageType       varchar(20) not null,
    start_date      date        not null,
    end_date        date        not null,
    primary key (id)
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

CREATE TABLE enrollment_students
(
    session_id BIGINT,
    user_id    BIGINT,
    PRIMARY KEY (session_id, user_id),
    CONSTRAINT fk_session
        FOREIGN KEY (session_id)
            REFERENCES session (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES ns_user (id)
            ON DELETE CASCADE
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
