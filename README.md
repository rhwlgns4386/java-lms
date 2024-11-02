# 학습 관리 시스템(Learning Management System)
## 진행 방법
* 학습 관리 시스템의 수강신청 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## Step1. 요구 사항
* 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다. 
* 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다. 
* 답변이 없는 경우 삭제가 가능하다. 
* 질문자와 답변글의 모든 답변자 같은 경우 삭제가 가능하다. 
* 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경 한다. 
* 질문자와 답변자가 다른경우 답변을 삭제할수 없다. 
* 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.

## Step1. 기능 구현
- [X] Audit 분리
- [X] 일급 콜렉션 래핑 (List<Answer> -> Answers)
- [X] 서비스 레이어 로직 이동
  - [X] Answer에 delete 책임 이동
  - [X] 삭제 시 DeleteHistory 반환
  - [X] Answers에서 일괄 적용
  - [X] Question에 delete 책임 이동
  - [X] 삭제 시 DeleteHistory 반환
  - [X] Answer, Question 주인이 아닐 시 삭제 불가 예외 추가
- [X] Question의 List<Answer> -> Answers로 대체

## Step1. 피드백 구현
- [X] Readme 에 체크리스트 적용
- [X] DeleteHistory에 정적 팩토리 메소드 패턴 구현
- [X] 접근 제어자 추가 (private)

## Step1. 리팩토링
- [X] 테스트 통과하게 DeleteHistory add 순서 변경
- [X] Answers에 있던 validation 체크 로직 answer 내부로 이동
- [X] DeleteHistory 생성, delete 메소드 분리 (CQRS 패턴)
- [X] question에서 title, body 분리

## Step2. 요구 사항
* 과정(Course)은 기수 단위로 운영하며, 여러 개의 강의(Session)를 가질 수 있다.
* 강의는 시작일과 종료일을 가진다.
* 강의는 강의 커버 이미지 정보를 가진다.
  * 이미지 크기는 1MB 이하여야 한다.
  * 이미지 타입은 gif, jpg(jpeg 포함),, png, svg만 허용한다.
  * 이미지의 width는 300픽셀, height는 200픽셀 이상이어야 하며, width와 height의 비율은 3:2여야 한다.
* 강의는 무료 강의와 유료 강의로 나뉜다.
  * 무료 강의는 최대 수강 인원 제한이 없다.
  * 유료 강의는 강의 최대 수강 인원을 초과할 수 없다.
  * 유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.
* 강의 상태는 준비중, 모집중, 종료 3가지 상태를 가진다.
* 강의 수강신청은 강의 상태가 모집중일 때만 가능하다.
* 유료 강의의 경우 결제는 이미 완료한 것으로 가정하고 이후 과정을 구현한다.
  * 결제를 완료한 결제 정보는 payments 모듈을 통해 관리되며, 결제 정보는 Payment 객체에 담겨 반한된다.

## Step2. 기능 구현
- [X] ImageSize validation 하는 클래스 구현
- [X] 이미지 확장자 관리 enum 구현
- [X] 강의 시작, 종료일 관리 클래스 구현
- [X] 이미지 관리 클래스 구현
- [X] 유료, 무료 강의 구현
- [X] 과정 구현

## Step2. 피드백 구현
- [X] SessionStatus 내부에 canEnroll 메소드 구현
- [X] Payments 클래스 삭제
- [X] enroll 메소드 매개변수 Payment로 수정 
- [X] sessionId string -> long 수정
- [X] Session 내 student에 userId 수집
- [X] Payment 내 지불한 금액 확인 메소드 추가 
- [X] PaidSession 내 지불한 금액 확인 validation 추가
- [X] FreeSession 내 지불한 금액 확인 validation 추가
- [X] 무료강의인 경우 isFree로 결제 정보의 책임을 Payment로 이동
- [X] Payment Fixture 분리
- [X] PaidSession Fixture 분리
- [X] FreeSession Fixture 분리
- [X] Course Fixture 분리
- [X] SessionDate Fixture 분리
- [X] SessionImageType Fixture 분리
- [X] SessionImage Fixture 분리

## Step3. 요구사항
* 도메인과 DB 테이블 매핑
* CRUD 구현

## Step3. 기능 구현
- [X] session_image 테이블 생성
- [X] session_image select, save 구현
- [X] session_image logger 사용해 출력부 추가
- [X] session 테이블 생성
- [X] session select, save 구현
- [X] session logger 사용해 출력부 추가
- [X] session student 연관 테이블 조회 및 객체 생성

## Step3. 리팩토링
- [X] SessionStudent Repository 분리

## Step3. 피드백 구현
- [X] SessionEntity 추가 (디비 접근용)
- [X] CourseSession select, save 구현
- [X] Course에서 포함되어있는 Session 같이 조회 
- [X] Entity 내부 메소드 이름 from으로 변경 (엔티티에서 호출하므로)

## Step4. 요구사항
* 강의 진행 상태 (준비중, 진행중, 종료)와 모집 상태 (비모집, 모집) 으로 상태 분리
* 강의는 하나 이상의 커버이미지 가질 수 있음.
* 강사가 수강 신청한 사람 중 선발된 인원에 대해서만 수강 승인 가능
* 강사가 수강 신청한 사람 중 선발되지 않은 인원은 수강 취소
* 컴파일 에러 최소화하면서 점진적인 리팩토링
* DB 테이블에 데이터가 존재한다는 가정하에 리팩토링 (기존에 쌓인 데이터 제거하지 않은 상태로 리팩토링)

## Step4. 기능구현
- [X] 강의 진행상태, 모집상태 구분 
  - [X] 컴파일 에러 적게끔 기존 상태를 모집 상태로 변경, 신규 진행상태 추가
  - [X] Session, PaidSession, FreeSession에 생성자 별도로 추가 (기존 에러 방지)
- [X] 데이터베이스 컬럼 추가 (progress_status, recruiting_status)
  - 기존 status 컬럼에 있던 값은 recruiting_status에 업데이트했다고 가정
  - 디비가 비어있을 경우 entity에서 예외처리
- [X] saveNew, findByIdNew 메소드 추가해서 스트랭글러 패턴 적용
  - 사용하지 않을 경우 삭제 예정
- [X] 세션 이미지 매핑 테이블 추가 (session_session_image)
- [X] 세션 이미지 select, save 구현
- [X] session 객체 생성자에 List<SessionImage> 추가
- [X] FreeSession Builder 추가
- [X] session select할 때, session_session_image 테이블 같이 조회
- [X] entity에 toDomainNew 메소드 추가 (기존거 병행)
- [X] paidSessionBuilder 생성 및 테스트 수정
- [X] 강의,학생 매핑 테이블에 상태 추가
- [X] 상태값 추가한 select, save 기능 추가
- [X] 강의 조회 시 승인된 사람만 수강생으로 조회
- [X] 강의 학생 매핑 테이블 update 기능 구현
- [X] session 에 applyStudents 추가,  approve, cancel 메소드 구현
- [X] 데이터 베이스 저장 시, approve, apply 구분해서 저장
- [X] 데이터 베이스 조회 시, approve, apply 구분해서 조회