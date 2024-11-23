package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.studentsessions.domain.StudentSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class FreeSession extends Session{
    public FreeSession(Long id, Course course, List<StudentSession> studentSessions, String title, List<Image> coverImages,
                       SessionProgressStatus sessionStatus, SessionRecruitmentStatus sessionRecruitmentStatus,
                       LocalDate startDate, LocalDate endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, course, studentSessions, title, coverImages, SessionFeeStatus.FREE, 0, sessionStatus, sessionRecruitmentStatus,
                startDate, endDate, createdAt, updatedAt);
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
        return Objects.equals(title, session.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
