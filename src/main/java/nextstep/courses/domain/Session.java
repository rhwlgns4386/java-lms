package nextstep.courses.domain;

import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.request.RequestOrderParam;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public abstract class Session {
    private SessionId sessionId;

    private SessionInfo sessionInfo;

    private SessionImages sessionImages;

    private SessionPrice salePrice;

    private Students students;

    private SessionType sessionType;

    public Session(SessionInfo sessionInfo, SessionImages sessionImages, SessionPrice salePrice, SessionType sessionType) {
        this(null, sessionInfo, sessionImages, salePrice, sessionType);
    }
    public Session(SessionId sessionId, SessionInfo sessionInfo, SessionImages sessionImages, SessionPrice salePrice, SessionType sessionType) {
        if (sessionInfo == null) {
            throw new IllegalArgumentException("강의 정보를 입력해주세요");
        }
        if (sessionImages == null || sessionImages.getSessionImages().isEmpty()) {
            throw new IllegalArgumentException("강의 이미지를 입력해주세요");
        }
        if (salePrice == null) {
            throw new IllegalArgumentException("강의 금액을 입력해주세요");
        }
        if (sessionType == null) {
            throw new IllegalArgumentException("강의 타입을 선택해주세요.");
        }

        this.sessionId = sessionId;
        this.sessionInfo = sessionInfo;
        this.sessionImages = sessionImages;
        this.salePrice = salePrice;
        this.sessionType = sessionType;
        this.students = new Students(Collections.emptyList());
    }

    public long getSalePrice() {
        return salePrice.getSalePrice();
    }

    public int getStateCode() {
        return sessionInfo.getStatusCode();
    }

    public int getStudentsSize() {
        return students.getSize();
    }

    public void validateOrderSessionStatus() {
        sessionInfo.validateOrderSessionStatus();
    }

    public void validateOrderSessionProgressCode() {
        sessionInfo.validateOrderSessionProgressCode();
    }

    public void updateStudent(NsUser student) {
        students.updateStudent(student);
    }

    public boolean isDuplicateStudent(NsUser student) {
        return students.isDuplicateStudent(student);
    }

    public void orderSession(RequestOrderParam param) throws CannotRegisteSessionException {
        this.orderSession(param);
    }

    public String getTitle() {
        return sessionInfo.getTitle();
    }

    public String getCreatorId() {
        return sessionInfo.getCreatorId();
    }

    public LocalDateTime getApplyStartDate() {
        return sessionInfo.getApplyStartDate();
    }

    public LocalDateTime getApplyEndDate() {
        return sessionInfo.getApplyEndDate();
    }

    public String getSessionTypeCode() {
        return sessionType.getTypeCode();
    }

    public void addStudents(Students students) {
        this.students = new Students(students.getStudents());
    }

    public NsUser getStudentIdx(int idx) {
        return students.getStudentIdx(idx);
    }

    public long getSessionId() {
        return sessionId.getSessionId();
    }

    public int getProgressCode() {
        return sessionInfo.getProgressCode();
    }

    public List<SessionImage> getSessionImages() {
        return sessionImages.getSessionImages();
    }

    public long getInstructorId(){
        return sessionInfo.getInstructorId();
    }

}
