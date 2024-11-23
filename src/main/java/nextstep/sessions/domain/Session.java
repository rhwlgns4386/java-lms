package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.global.domain.BaseEntity;
import nextstep.studentsessions.domain.RegistrationApprovalStatus;
import nextstep.studentsessions.domain.StudentSession;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public abstract class Session extends BaseEntity {
    protected Course course;
    protected SessionFeeStatus feeStatus;
    protected String title;
    protected List<Image> coverImages;
    protected Integer fee;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected SessionProgressStatus sessionStatus;
    protected SessionRecruitmentStatus sessionRecruitmentStatus;
    protected List<StudentSession> studentSessions;

    protected Session(Long id, Course course, List<StudentSession> studentSessions, String title, List<Image> coverImages, SessionFeeStatus feeStatus,
                      Integer fee, SessionProgressStatus sessionStatus, SessionRecruitmentStatus sessionRecruitmentStatus,
                      LocalDate startDate, LocalDate endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.course = course;
        this.studentSessions = studentSessions;
        this.title = title;
        this.coverImages = coverImages;
        this.fee = fee;
        this.feeStatus = feeStatus;
        this.sessionStatus = sessionStatus;
        this.sessionRecruitmentStatus = sessionRecruitmentStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void registerSession(NsUser loginUser, LocalDateTime createdAt) {
        validateSession();

        addStudentSession(
                new NsStudent(loginUser, createdAt),
                RegistrationApprovalStatus.PENDING,
                createdAt
        );
    }

    private void addStudentSession(NsStudent student, RegistrationApprovalStatus registrationApprovalStatus, LocalDateTime createdAt) {
        StudentSession studentSession = new StudentSession(this, student, registrationApprovalStatus, createdAt);
        this.studentSessions.add(studentSession);
        student.registerSession(studentSession);
    }

    private void validateSession() {
        this.sessionStatus.checkRegisterAvailable();
        this.sessionRecruitmentStatus.checkRegisterAvailable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setRelatedEntities(Course course, List<Image> coverImages, List<StudentSession> studentSessions) {
        this.course = course;
        this.studentSessions = studentSessions;
        this.coverImages = coverImages;
    }
}
