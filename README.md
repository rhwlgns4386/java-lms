# 학습 관리 시스템(Learning Management System)

## 진행 방법

* 학습 관리 시스템의 수강신청 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정

* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

### 기능목록

- [x] 수강신청 금액과 세션의 요금과 같은지 확인한다
- [x] 수간신청 금액과 세션의 요금이 다른경우 예외를 발생시킨다
- [x] 금액은 음 수 인지 검증한다
- [ ] 모집상태가 진행중인지 확인한다
- [ ] 수강 상태를 기준으로 모집상태를 검색한다
- [x] 이미지 byte크기를 검사한다
- [x] Image 해상도 비율을 검증한다
- [x] Image 넓이는 300 픽셀 높이는 200픽셀 이상인지검증한다
- [x] 픽셀 값이 음수인지 검증한다
- [x] 이미지 타입을 검색한다
- [x] 지원하지 않는 이미지타입이면 예외를 발생한다
- [x] 종료일은 시작일보다 빠른지 검증한다
- [x] 이미 신청한 유저인지 검증한다
- [x] 수강신청자 강좌에 등록한다
- [x] 강의를 무료강의를 생성한다
- [x] 유료강의를 생성한다
- [x] 수강신청시 최대 인원이 넘어가면 예외 발생
- [x] 수강신청시 최대인원이 넘어가는지 확인 한다
- [x] 수강신청 최대 인원은 음수인지 검증한다
- [x] 픽셀의 크기가 작거나 같은지 확인 한다
- [x] 수강상태가 진행중이 아니라면 예외를 발생한다
