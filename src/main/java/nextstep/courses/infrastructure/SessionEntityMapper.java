package nextstep.courses.infrastructure;

import static nextstep.courses.factory.SessionFactory.session;

import java.time.LocalDate;
import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.LimitedEnrollments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionStatus;

public class SessionEntityMapper {

    public Session toEntity(long id, int charge, Integer capacity, Set<EnrollmentStudent> enrolledStudents,
                            SessionStatus sessionStatus,
                            String fileName,
                            int width, int height, int size, ImageType imageType, LocalDate startDate,
                            LocalDate endDate) {
        return session(id, charge, enrollments(capacity, sessionStatus, enrolledStudents), fileName,
                width, height, size, imageType, startDate, endDate);
    }

    private Enrollments enrollments(Integer capacity, SessionStatus sessionStatus,
                                    Set<EnrollmentStudent> enrolledStudents) {
        if (capacity == null) {
            return new Enrollments(sessionStatus, enrolledStudents);
        }

        return new LimitedEnrollments(capacity, sessionStatus, enrolledStudents);
    }
}
