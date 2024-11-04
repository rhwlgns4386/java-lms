package nextstep.courses.domain;

import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.request.RequestOrderParam;
import nextstep.courses.collection.Students;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Collections;

public class Session {
    //구성이 필요한 관계는 이렇게
    private SessionInfo sessionInfo;

    private SessionImage sessionImage;

    private long salePrice;

    private StateCode stateCode;//이넘이니까 클래스로 받아도되네

    private Students students;

    private SessionType sessionType;

    public Session() {
    }

    public Session(String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate,
                   long salePrice, StateCode stateCode, String createId,
                   int fileSize, String type, int width, int height, String fileName, SessionType sessionType) {
        this(title, applyStartDate, applyEndDate, salePrice, stateCode, createId,
                fileSize, type, width, height, fileName, new Students(Collections.emptyList()), sessionType);
    }

    public Session(String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate,
                   long salePrice, StateCode stateCode, String createId,
                   int fileSize, String type, int width, int height, String fileName, Students students, SessionType sessionType) {
        this.sessionInfo = new SessionInfo(title, applyStartDate, applyEndDate, createId);
        this.stateCode = stateCode;
        this.salePrice = salePrice;
        this.sessionImage = new SessionImage(fileSize, type, width, height, fileName);
        this.students = students;
        this.sessionType = sessionType;
    }

    public Session(SessionInfo sessionInfo, SessionImage sessionImage, long salePrice, StateCode stateCode) {
        this(sessionInfo, sessionImage, salePrice, stateCode, SessionType.FREE);
    }

    public Session(SessionInfo sessionInfo, SessionImage sessionImage, long salePrice, StateCode stateCode, SessionType sessionType) {
        this.sessionInfo = sessionInfo;
        this.sessionImage = sessionImage;
        this.salePrice = salePrice;
        this.stateCode = stateCode;
        this.sessionType = sessionType;
        this.students = new Students(Collections.emptyList());
    }

    public long getSalePrice() {
        return salePrice;
    }

    public int getStateCode() {
        return stateCode.getStatusCode();
    }

    public int getStudentsSize() {
        return students.getSize();
    }

    public void validateOrderSessionStatus() {
        stateCode.validateOrderSessionStatus();
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
}
