# 학습 관리 시스템(Learning Management System)
## 진행 방법
* 학습 관리 시스템의 수강신청 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## STEP2 수강신청 도메인 설계

* 강의 session 는 아래 요소로 구성 
  * 객체 설계 (구성요소 및 관련 기능) - 도메인
    * 강의 기간  
      - [x] 시작종료일 검증 : 시작일 > 종료일 : 오류 
      - [x] 시작/종료일 빈값검증
    * 강의 유형
      * 유료강의 (최대인원과 금액 추가 관리 필요) : 
        - [x] 최대인원 초과 여부 체크  
        - [x] 결제 금액 일치 여부 체크 
    * 강의 상태
      - [x] 수강 신청 가능여부 확인 (Enum 통해 확인)
    * 강의 이미지
      - [ ] 이미지 등록
      - [ ] 이미지 변경
      - [ ] 이미지 규긱 검증
      - [ ] 이미지 조회 - 도메인의 역할이 아닌가 ?
    * 강의 수강 내역 
      - [ ] 수강 내역 
  * 강의 서비스 기능 
    - [ ] 강의 등록 (관리자) 
    - [ ] 강의 신청 (사용자)
    - [ ] 강의 정보 변경(관리자)
