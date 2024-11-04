package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Session {

    protected Long sessionId;
    protected SessionDate date;
    protected SessionImage image;
    protected List<SessionImage> images;
    protected RecruitingStatus recruitingStatus;
    protected ProgressStatus progressStatus;
    protected List<Long> students;
    protected List<Long> applyStudents = new ArrayList<>();

    public Session(Long sessionId, SessionDate date, SessionImage image, RecruitingStatus recruitingStatus, List<Long> numOfStudents) {
        this.sessionId = sessionId;
        this.date = date;
        this.image = image;
        this.recruitingStatus = recruitingStatus;
        this.students = numOfStudents;
    }

    public Session(Long sessionId, SessionDate date, SessionImage image, RecruitingStatus recruitingStatus, ProgressStatus progressStatus, List<Long> students) {
        this.sessionId = sessionId;
        this.date = date;
        this.image = image;
        this.recruitingStatus = recruitingStatus;
        this.progressStatus = progressStatus;
        this.students = students;
    }

    public Session(Long sessionId, SessionDate date, List<SessionImage> images, RecruitingStatus recruitingStatus, ProgressStatus progressStatus, List<Long> students, List<Long> applyStudents) {
        this.sessionId = sessionId;
        this.date = date;
        this.images = images;
        this.recruitingStatus = recruitingStatus;
        this.progressStatus = progressStatus;
        this.students = students;
        this.applyStudents = applyStudents;
    }

    public void enroll(Payment payment) {
        if (!recruitingStatus.canEnroll()) {
            throw new IllegalStateException("강의가 모집 상태가 아닙니다.");
        }
        this.applyStudents.add(payment.getNsUserId());
    }

    public void approve(Long id) {
        if (!applyStudents.contains(id)) {
            throw new IllegalArgumentException("수강신청을 하지 않았습니다.");
        }
        applyStudents.remove(id);
        students.add(id);
    }

    public void cancel(Long id) {
        if (!applyStudents.contains(id)) {
            throw new IllegalArgumentException("수강신청을 하지 않았습니다.");
        }
        applyStudents.remove(id);
    }

    public boolean isContainUser(NsUser user) {
        return students.contains(user.getId());
    }

    public Long getSessionId() {
        return sessionId;
    }

    public SessionDate getDate() {
        return date;
    }

    public SessionImage getImage() {
        return image;
    }

    public RecruitingStatus getRecruitingStatus() {
        return recruitingStatus;
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public List<Long> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public List<SessionImage> getImages() {
        return Collections.unmodifiableList(images);
    }

    public List<Long> getApplyStudents() {
        return applyStudents;
    }
}
