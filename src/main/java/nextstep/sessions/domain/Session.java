package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.global.domain.BaseEntity;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Session extends BaseEntity {
    protected Course course;
    protected SessionFeeStatus feeStatus;
    protected String title;
    protected Image coverImage;
    protected Integer fee;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected SessionStatus sessionStatus;

    protected List<NsStudent> students;

    protected Session(Long id, Course course, List<NsStudent> students, String title, Image coverImage, SessionFeeStatus feeStatus,
                      Integer fee, SessionStatus sessionStatus, LocalDate startDate, LocalDate endDate,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.course = course;
        this.students = students;
        this.title = title;
        this.coverImage = coverImage;
        this.fee = fee;
        this.feeStatus = feeStatus;
        this.sessionStatus = sessionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void registerSession(NsUser loginUser, LocalDateTime createdAt) {
        this.validateSession();

        NsStudent student = new NsStudent(loginUser, createdAt);
        this.students.add(student);
        student.registerSession(this);
    }

    private void validateSession() {
        this.sessionStatus.checkRegisterAvailable();
    }
}
