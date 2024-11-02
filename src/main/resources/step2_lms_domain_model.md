수강 신청 기능 요구사항

-[x] 과정(Course)은 기수 단위로 운영하며, 여러 개의 강의(Session)를 가질 수 있다.
    - 과정에 sessions 일급컬렉션
-[x] 강의(Session)는 시작일과 종료일을 가진다. start~end 필드
-[x] 이미지 강의는 강의 커버 이미지 정보를 가진다.
-[x] 이미지 크기는 1MB 이하여야 한다.
    - 이미지 크기 validation
-[x] 이미지 타입은 gif, jpg(jpeg 포함),png, svg만 허용한다.
-  이미지 확장자 validation
-[x] 이미지의 width는 300픽셀, height는 200픽셀 이상이어야 하며, width와 height의 비율은 3:2여야 한다.
- 이미지 픽셀 validation 

강의는 무료 강의와 유료 강의로 나뉜다.
- FreeSession / PaidSession
- 무료강의 로직과 유료강의 로직이 더 있을 수 있고 다를테니 클래스 분리하는건 어떨까하고 분리하게됨
- 무료 강의는 최대 수강 인원 제한이 없다.
- studentCount

-[x] 유료 강의는 강의 최대 수강 인원을 초과할 수 없다.
- 수강 인원 validation 
-[x] 유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.
- PaidSession validation
-[x] 강의 상태는 준비중, 모집중, 종료 3가지 상태를 가진다.
- stateCode 10 준비 20 모집중 30 종료
-[x] 강의 수강신청은 강의 상태가 모집중일 때만 가능하다.
- 모집 validation
유료 강의의 경우 결제는 이미 완료한 것으로 가정하고 이후 과정을 구현한다.

결제를 완료한 결제 정보는 payments 모듈을 통해 관리되며, 결제 정보는 Payment 객체에 담겨 반한된다.

Service 틀만 작성 
-[x] 강의를 등록한다.
-[x] 수강 신청을한다
