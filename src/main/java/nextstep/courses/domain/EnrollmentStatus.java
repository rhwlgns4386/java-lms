package nextstep.courses.domain;

import java.util.Objects;

public class EnrollmentStatus {
    private SessionStatus sessionStatus;
    private RecruitmentStatus recruitmentStatus;

    public EnrollmentStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
        this.recruitmentStatus = RecruitmentStatus.findBySessionStatus(sessionStatus);
    }

    boolean isRecruiting() {
        return recruitmentStatus.isRecruiting();
    }

    public SessionStatus sessionStatus() {
        return sessionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnrollmentStatus that = (EnrollmentStatus) o;
        return sessionStatus == that.sessionStatus && recruitmentStatus == that.recruitmentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionStatus, recruitmentStatus);
    }
}
