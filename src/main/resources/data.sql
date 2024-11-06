INSERT INTO ns_user (id, user_id, password, name, email, created_at)
values (1, 'javajigi', 'test', '자바지기', 'javajigi@slipp.net', CURRENT_TIMESTAMP());
INSERT INTO ns_user (id, user_id, password, name, email, created_at)
values (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net', CURRENT_TIMESTAMP());
INSERT INTO ns_user (id, user_id, password, name, email, created_at)
values (3, '333', 'test', '삼삼지기', 'sam@slipp.net', CURRENT_TIMESTAMP());

INSERT INTO question (id, writer_id, title, contents, created_at, deleted)
VALUES (1, 1, '국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?',
        'Ruby on Rails(이하 RoR)는 2006년 즈음에 정말 뜨겁게 달아올랐다가 금방 가라 앉았다. Play 프레임워크는 정말 한 순간 잠시 눈에 뜨이다가 사라져 버렸다. RoR과 Play 기반으로 개발을 해보면 정말 생산성이 높으며, 웹 프로그래밍이 재미있기까지 하다. Spring MVC + JPA(Hibernate) 기반으로 진행하면 설정할 부분도 많고, 기본으로 지원하지 않는 기능도 많아 RoR과 Play에서 기본적으로 지원하는 기능을 서비스하려면 추가적인 개발이 필요하다.',
        CURRENT_TIMESTAMP(), false);

INSERT INTO answer (writer_id, contents, created_at, question_id, deleted)
VALUES (1,
        'http://underscorejs.org/docs/underscore.html Underscore.js 강추합니다! 쓸일도 많고, 코드도 길지 않고, 자바스크립트의 언어나 기본 API를 보완하는 기능들이라 자바스크립트 이해에 도움이 됩니다. 무엇보다 라이브러리 자체가 아주 유용합니다.',
        CURRENT_TIMESTAMP(), 1, false);

INSERT INTO answer (writer_id, contents, created_at, question_id, deleted)
VALUES (2,
        '언더스코어 강력 추천드려요. 다만 최신 버전을 공부하는 것보다는 0.10.0 버전부터 보는게 더 좋더군요. 코드의 변천사도 알 수 있고, 최적화되지 않은 코드들이 기능은 그대로 두고 최적화되어 가는 걸 보면 재미가 있습니다 :)',
        CURRENT_TIMESTAMP(), 1, false);

INSERT INTO question (id, writer_id, title, contents, created_at, deleted)
VALUES (2, 2, 'runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?',
        '설계를 희한하게 하는 바람에 꼬인 문제같긴 합니다만. 여쭙습니다. 상황은 mybatis select 실행될 시에 return object 의 getter 가 호출되면서인데요. getter 안에 다른 property 에 의존중인 코드가 삽입되어 있어서, 만약 다른 mybatis select 구문에 해당 property 가 없다면 exception 이 발생하게 됩니다.',
        CURRENT_TIMESTAMP(), false);

-- 세션 테이블에 데이터 삽입
insert into session (title, apply_start_date, apply_end_date, sale_price, state_code, PROGRESS_CODE, creator_id,
                     session_type, STUDENT_MAX_COUNT)
values ('제목1유료_비모집_준비중', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 1000, 30, 10, 'creatorId1', '20', 0),
       ('제목2유료_비모집_진행중', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 1000, 30, 20, 'creatorId1', '20', 0),
       ('제목3유료_모집_준비중', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 2000, 20, 10, 'creatorId1', '20', 0),
       ('제목4유료_모집_진행중', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 2000, 20, 20, 'creatorId1', '20', 0),
       ('제목5유료_모집_강의종료', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 2000, 20, 30, 'creatorId1', '20', 0),
       ('제목6무료_비모집_진행중', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 0, 30, 20, 'creatorId2', '10', 2),
       ('제목7무료_비모집_강의종료', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 0, 20, 30, 'creatorId2', '10', 2),
       ('제목8무료_모집_준비중', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 0, 20, 10, 'creatorId2', '10', 2),
       ('제목9무료_모집_진행중', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 0, 20, 20, 'creatorId2', '10', 2),
       ('제목10무료_모집_강의종료', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30' DAY, 0, 20, 30, 'creatorId2', '10', 2);

insert into SESSION_ORDER(SESSION_ID, NS_USER_ID, SALE_PRICE, ORD_STAT_CODE, APPR_ID)
values ( 1, 1, 1000, 10, null), --1번강의 1번유저 대기중
       ( 1, 2, 1000, 10, null), --1번강의 2번유저 대기중
       ( 1, 3, 1000, 20, 5), --1번강의를 듣는 3번유저를 5번강사가 승인함
       ( 2, 3, 1000, 30, 5); --2번강의를 듣는 3번유저를 5번강사가 승인거절함
;


-- 세션 이미지 테이블에 데이터 삽입
insert into session_image (session_id, file_name, file_size, type, width, height)
values (1, 'imageFileName3', 1000, 'jpg', 300, 200),
       (1, 'imageFileName4', 2000, 'png', 600, 400),
       (1, 'imageFileName4', 2000, 'png', 600, 400),
       (1, 'imageFileName4', 2000, 'png', 600, 400),
      -- (1, 'imageFileName3', 2000, 'png', 600, 400),
       (2, 'imageFileName2', 1000, 'png', 600, 400),
       (3, 'imageFileName3', 1000, 'png', 1200, 800),
       (4, 'imageFileName4', 1000, 'png', 1200, 800),
       (5, 'imageFileName5', 1000, 'jpg', 300, 200),
       (6, 'imageFileName6', 1000, 'png', 600, 400),
       (7, 'imageFileName7', 1000, 'png', 1200, 800),
       (8, 'imageFileName8', 1000, 'png', 1200, 800),
       (9, 'imageFileName9', 1000, 'png', 1200, 800),
       (10, 'imageFileName10', 1000, 'png', 1200, 800);

SELECT s.title,
       s.apply_start_date,
       s.apply_end_date,
       s.sale_price,
       s.state_code,
       s.creator_id,
       s.session_type,
       s.STUDENT_MAX_COUNT,
       si.file_size,
       si.type,
       si.width,
       si.height,
       si.file_name
FROM session s
         LEFT JOIN session_image si ON s.SESSION_ID = si.SESSION_ID
WHERE s.SESSION_ID = 1;

SELECT s.title,
       s.apply_start_date,
       s.apply_end_date,
       s.sale_price,
       s.state_code,
       s.creator_id,
       si.file_size,
       si.type,
       si.width,
       si.height,
       si.file_name,
       s.session_type,
       s.student_max_count,
       s.SESSION_ID,
       s.PROGRESS_CODE
FROM session s
         LEFT JOIN session_image si ON s.SESSION_ID = si.SESSION_ID
WHERE s.SESSION_ID = 1;

SELECT so.NS_USER_ID,
       so.SESSION_ID
FROM session s
         LEFT JOIN PUBLIC.SESSION_ORDER SO on s.SESSION_ID = SO.SESSION_ID
where s.SESSION_ID = 1;

select *
from session;
select *
From NS_USER;
select ORDER_ID, SESSION_ID, NS_USER_ID, SALE_PRICE, ORD_STAT_CODE, APPR_ID
from SESSION_ORDER;

//insert into SESSION_ORDER(session_id, ns_user_id, sale_price) values (1, 1, 1000);

select *
from SESSION_IMAGE;

