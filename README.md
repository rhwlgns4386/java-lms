# 학습 관리 시스템(Learning Management System)

## 1. 요구 사항
### course 1 … n session
* session
    * 시작일과 종료일
    * 강의 커버 이미지 존재
        * 이미지 크기 1mb 이하
        * 이미지 타입 gif, jpg(jpeg), png, sig
        * 크기 300px * 200px 이상
    * 무료 강의
        * 수강 인원 제한 없음
    * 유료 강의
        * 수강 인원 최대 수강 인원 초과 불가
        * 결제한 금액과 수강료가 일치 할 때 수강신청 완료
    * 강의 상태
        * 준비중
        * 모집중
        * 종료
    * 강의 수강 신청 -> 모집중 일때에만 가능
    * 유료 강의 -> 결제 이미 완료로 가정
        * 경제 완료 결제 정보는 payments 모듈을 통해 관리
        * 결제 정보 -> Payment 객체

## 2. 기능목록
* [x] 강의 커버 객체 생성 기능
    * [x] 이미지 크기 1mb 검증
    * [x] 이미지 타입 검증
    * [x] 크기 300px * 200px 이상 검증
* [ ] Session(강의) 추상 클래스
    * [ ] 강의 커버 공통적으로 가짐
    * [ ] 강의 상태
      * 준비중
      * 모집중
      * 종료
    * [ ] 수강 가능 유효성 검증
      * 무료 : 수강 인원 제한 없음
      * 유료 : 수강 인원 초과 불가
    * [ ] 수강 신청 기능 
        * [ ] payments 모듈로 결제 기능 넘김 -> 일단 요구사항으로 무조건 승인
        * [ ] Payment <-> Session 연결한 Payment 객체 반환 받음
        * [ ] Payment 객체를 검증하여 금액이 맞으면 수강 신청 완료
            * [ ] Payment 객체 persistence
            * [ ] 수강 인원 증가
            * [ ] Session 객체 update

* [ ] 수강 신청 기능
    * [ ] 수강하고자 하는 강의 객체 가져옴
    * [ ] 수강 가능 여부 검증
    * [ ] 수강 신청 가능하면 수강 신청 진행
