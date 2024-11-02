# 기능 구현 및 도메인 정리


## TODO

- [ ] 서비스 구현

### DB - Session
- [x] 저장
- [x] 찾기

### DB - SessionCoverImage
- [x] 저장
- [x] 찾기

### DB - SessionStudent
- [x] 강의에 등록 시 유저가 수강 신청했는 지 Optional로 반환
  - Course는 기수 단위로 운영되므로 강의는 중복 결제를 하면 안됨
- [x] 저장

## DONE

### Course (과정)


### Session (강의)
- 모집중이 아니라면 수강 신청 시 예외
- 수강신청 시 모집마감이면 예외

### SessionPay (강의 결제 정보)
- 결제한 수강료와 강의 가격 검증

### SessionPayType (강의유형 - [무료, 유료])
- 문자열로 객체 반환

### SessionStudents (강의 수강생)

- 최대 인원 다 찼는지 판별

### SessionStatus (강의 상태 - [준비중, 모집중, 종료])
- 수강 신청 시 모집중이 아니라면 예외 처리

### SessionPeriod (강의 기간)
- 종료일이 시작일 이전이면 예외처리
- 시작일과 종료일 수강신청 범위 밖 예외 처리

### SessionCoverImage (강의 커버 이미지)
- 이미지 크기가 1MB 보다 크면 예외처리

### SessionCoverImagePath
- 파일명에 특수문자가 있으면 예외처리

### SessionCoverImageType (강의 커버 이미지 확장자 타입 - [gif, jpg, jpeg, png, svg])
- 일치하는 확장자 검증
- 허가하지 않는 확장자일 시 예외 발생 

### SessionCoverImageSize (강의 커버 이미지 사이즈)
- 높이(200px), 너비(300px), 비율(3:2) 검증

