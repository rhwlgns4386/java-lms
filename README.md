# 기능 구현 및 도메인 정리

## 4단계 요구사항
- 강의 수강신청은 강의 상태가 모집중일 때만 가능하다.
    - 강의가 진행 중인 상태에서도 수강신청이 가능해야 한다.
    - 강의 진행 상태(준비중, 진행중, 종료)와 모집 상태(비모집중, 모집중)로 상태 값을 분리해야 한다.

### SessionProgressStatus(강의 진행 상태), SessionRecruitStatus(모집 상태)
- [x] 문자열로 검색, 지원 가능한지 여부 반환 

### SessionApplyStatus (강의 지원 상태)
- [x] 검증(진행중, 모집중)둘 다 아니면 예외
- [x] SessionStatus유지 하고(SessionProgressStatus, SessionRecruitStatus - null 허용하여 컬럼생성)
- [x] 수강 신청시 모집중 or 진행중 상태에서만 가능

---

- 강의는 강의 커버 이미지 정보를 가진다.
    - 강의는 하나 이상의 커버 이미지를 가질 수 있다.

- [ ] SessionCoverImages 도메인 추가 하여 처리

---

- 강사가 승인하지 않아도 수강 신청하는 모든 사람이 수강 가능하다.
    - 우아한테크코스(무료), 우아한테크캠프 Pro(유료)와 같이 선발된 인원만 수강 가능해야 한다.
        - 강사는 수강신청한 사람 중 선발된 인원에 대해서만 수강 승인이 가능해야 한다.
        - 강사는 수강신청한 사람 중 선발되지 않은 사람은 수강을 취소할 수 있어야 한다.

### SessionApplies (수강 신청 목록)
- [ ] 취소하면 SessionApply 객체 제거
- [ ] 승인하면 SessionStudent로 객체 생성하여 save

### SessionApply (수강 신청)
- GUEST는 선발되지 않은 회원이라 가정

- [ ] GUEST(선발되지 않은 사람)면 취소 가능
- [ ] GUEST(선발되지 않은 사람)면 수강 승인 시 예외
- [ ] GUEST가 아니면(선발된 회원) 수강 취소 시 예외
- [ ] GUEST가 아니면(선발된 회원)면 수강 가능


---
# 이전 STEP

## DONE
- [x] 서비스 구현

### DB - Session
- [x] 저장
- [x] 찾기

### DB - SessionCoverImage
- [x] 저장
- [x] 찾기

### DB - SessionStudent (수강신청 확정)
- [x] sessionId로 리스트 반환
- [x] 저장

### Course (과정)


### Session (강의)
- [x] 모집중이 아니라면 수강 신청 시 예외
- [x] 수강신청 시 모집마감이면 예외

### SessionPay (강의 결제 정보)
- [x] 결제한 수강료와 강의 가격 검증

### SessionPayType (강의유형 - [무료, 유료])
- [x] 문자열로 객체 반환

### SessionStudents (강의 수강생)
- [x] 등록 시 중복 수강 신청 인지 검증
- [x] 최대 인원 다 찼는지 판별

### SessionStudent (수강신청 확정)

### SessionStatus (강의 상태 - [준비중, 모집중, 종료])
- [x] 수강 신청 시 모집중이 아니라면 예외 처리

### SessionPeriod (강의 기간)
- [x] 종료일이 시작일 이전이면 예외처리
- [x] 시작일과 종료일 수강신청 범위 밖 예외 처리

### SessionCoverImage (강의 커버 이미지)
- [x] 이미지 크기가 1MB 보다 크면 예외처리

### SessionCoverImagePath
- [x] 파일명에 특수문자가 있으면 예외처리

### SessionCoverImageType (강의 커버 이미지 확장자 타입 - [gif, jpg, jpeg, png, svg])
- [x] 일치하는 확장자 검증
- [x] 허가하지 않는 확장자일 시 예외 발생

### SessionCoverImageSize (강의 커버 이미지 사이즈)
- [x] 높이(200px), 너비(300px), 비율(3:2) 검증

