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
      - [x] 크기 검증(ImageSize)
      - [x] 가로/세로 크기 (ImageWidth, ImageHeight)
      - [x] 비례 검증 (SessionImagePixel)
    * 강의 수강 내역 
      - [x] 수강 신청 이력 
  * 강의 서비스 기능 (대상외) 
    - [ ] 강의 등록 (관리자) 
    - [ ] 강의 신청 (사용자)
    - [ ] 강의 정보 변경(관리자)

## STEP3 : 수강신청 도메인 테이블 설계 

* 테이블
  * 신청 내역 (application_detail) : 강의 신청 내역 관리 
    - [ ] : 컬럼 : id(일련번호), status_code(강의 신청 상태코드:신청, 승인), session_id(신청강의식별자), user_id(신청자식별자), created_ad(생성일), updated_at(갱신일)  
  * 강의 기본 (ns_session)
    - [ ] : 컬럼 : id(일련번호), start_date(시작일), end_date(종료일),status_code(상태코드),
            type_code(강의유형코드), maximum_number_of_applicants(최대신청인원), fee_amount(수강료), created_ad(생성일), updated_at(갱신일) 
  * 강의 이미지(session_image)
    - [ ] : 컬럼 : id(일련번호), url(저장주소), image(이미지), session_id(강의식별자), created_ad(생성일), updated_at(갱신일)

* repository 기능 
  * 신청내역 저장소 (ApplicationDetailRepository)
    - [ ] save : 신청내역 저장 
    - [ ] modifyStatus : 신청내역 상태 변경
    - [ ] findByUserAndSession
    - [ ] getBySession
  * 강의 저장소 (SessionRepository)
    - [ ] save : 강의 등록 
    - [ ] modifyStatus : 강의 상태 변경 
    - [ ] modifyPeriod : 강의 기간 변경
    - [ ] modifyTypeInfo : 강의 유형정보 변경
    - [ ] findById : 강의 조회
  * 강의 이미지 저장소(SessionImageRepository)
    - [ ] save : 강의 커버 이미지 등록
    - [ ] findBySession : 강의 이미지 조회 
* service 기능 (대상외 - 대략 구현)
  - [ ] 강의 등록 (관리자)
  - [ ] 강의 신청 (사용자)
  - [ ] 강의 정보 변경(관리자)


## STEP4 수강신청(요구사항 변경)

* 강의 상태 추가 : 모집상태 (비모집중, 모집중)
 - [ ] 컬럼 추가 
 - [ ] 기능 변경 : 모집상태에 따른 수강가능여부 판단 

* 강의는 하나 이상의 커버 이미지 가질 수 있다. 
 - [ ] 강의 : 강의이미지 ->  1 : N으로 변경 

* 수강 내역 상태 추가 
 - [ ] 컬럼 추가 : 수강 상태 [신청, 승인, 취소]
 - [ ] 기능 추가 : 수강승인, 수강 취소 

