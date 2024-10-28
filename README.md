# 학습 관리 시스템(Learning Management System)
## 진행 방법
* 학습 관리 시스템의 수강신청 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

# STEP1 TODO

# STEP1 DONE
- [x] 질문 삭제하기 요구사항
  - [x] 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
  - [x] 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
  - [x] 답변이 없는 경우 삭제가 가능하다.
  - [x] 질문자와 답변글의 모든 답변자 같은 경우 삭제가 가능하다.
  - [x] 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경 한다.
  - [x] 질문자와 답변자가 다른경우 답변을 삭제 할 수 없다.
  - [x] 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.
- [x] 리팩터링 요구사항
  - [x] QnaService의 deleteQuestion() 메서드에 단위 테스트 가능한 코드(핵심 비지니스 로직)를 도메인 모델 객체에 구현한다.
  - [x] QnaService의 비지니스 로직을 도메인 모델로 이동하는 리팩터링을 진행할 때 TDD로 구현한다. (리프 객체부터 구현)
  - [x] 도메인 모델로 로직을 이동한 후에도 QnaServiceTest의 모든 테스트는 통과해야 한다.
- [x] 힌트
  - [x] 객체의 상태 데이터를 꺼내지(get)말고 메시지를 보낸다.
  - [x] 규칙 8: 일급 콜렉션을 쓴다. Question의 List를 일급 콜렉션으로 구현해 본다.
  - [x] 규칙 7: 3개 이상의 인스턴스 변수를 가진 클래스를 쓰지 않는다. 인스턴스 변수의 수를 줄이기 위해 도전한다.
  - [x] 도메인 모델에 setter 메서드 추가하지 않는다
- [x] 피드백
  - [x] deleteWithAnswers로 확인 가능한 테스트 제거 
  - [x] DeleteHistory 정적 팩토리 메서드 제거(현재 불필요하다고 판단)
  - [x] DeleteHistoriesTest 불필요 테스트 제거
  - [x] Question 내부 미사용 메서드 제거
  - [x] 동시 삭제 히스토리 외부 삭제 시간 주입 
  - [x] answers 삭제의 validate -> detelte 메소드 내부로 이동
  - [x] deleteHisories 삭제

