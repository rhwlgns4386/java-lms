package nextstep.courses.domain;

import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.request.RequestOrderParam;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Collections;

public class Session {
    //구성이 필요한 관계는 이렇게
    private SessionInfo sessionInfo;

    private SessionImage sessionImage;

    private SessionPrice salePrice;

    private Students students;

    private SessionType sessionType;

    public Session() {
    }

    public Session(SessionInfo sessionInfo, SessionImage sessionImage, SessionPrice salePrice) {
        this(sessionInfo, sessionImage, salePrice, SessionType.FREE); //기존꺼를 유지하고 이렇게 SessionType.Free 기본 값 해도되나요? 나중에 사이드나 영향범위 없을까요?
    }

    public Session(SessionInfo sessionInfo, SessionImage sessionImage, SessionPrice salePrice, SessionType sessionType) {
        this.sessionInfo = sessionInfo;
        this.sessionImage = sessionImage;
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
        return sessionInfo.getSessionId();
    }

    public int getProgressCode() {
        return sessionInfo.getProgressCode();
    }

}
