package nextstep.courses.entity;

import lombok.Getter;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionBuilder;
import nextstep.courses.type.SessionState;
import nextstep.courses.type.SessionType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static nextstep.courses.domain.session.Session.NOT_ASSIGNED;

@Getter
public class SessionEntity {

    private final Long id;
    private final String coverFilePath;
    private final SessionState sessionState;
    private final int enrollment;
    private final int maxEnrollment;
    private final long sessionFee;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public SessionEntity(Long id, String coverFilePath, SessionState sessionState,
                         int enrollment, int maxEnrollment, long sessionFee,
                         Timestamp startDate, Timestamp endDate) {
        this(id, coverFilePath, sessionState, enrollment, maxEnrollment,
                sessionFee, toLocalDateTime(startDate), toLocalDateTime(endDate));
    }

    public SessionEntity(String coverFilePath, SessionState sessionState,
                         int enrollment, int maxEnrollment, long sessionFee,
                         Timestamp startDate, Timestamp endDate) {
        this(null, coverFilePath, sessionState, enrollment, maxEnrollment,
                sessionFee, toLocalDateTime(startDate), toLocalDateTime(endDate));
    }

    public SessionEntity(String coverFilePath, SessionState sessionState,
                         int enrollment, int maxEnrollment, long sessionFee,
                         LocalDateTime startDate, LocalDateTime endDate) {
        this(null, coverFilePath, sessionState, enrollment, maxEnrollment, sessionFee, startDate, endDate);
    }

    public SessionEntity(Long id, String coverFilePath, SessionState sessionState,
                         int enrollment, int maxEnrollment, long sessionFee,
                         LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.coverFilePath = coverFilePath;
        this.sessionState = sessionState;
        this.enrollment = enrollment;
        this.maxEnrollment = maxEnrollment;
        this.sessionFee = sessionFee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    public Session toDomain() {
        SessionBuilder sessionBuilder = SessionBuilder.builder()
                .id(id)
                .sessionState(sessionState)
                .coverImage(coverFilePath)
                .enrollment(enrollment)
                .maxEnrollment(maxEnrollment)
                .startDate(startDate)
                .endDate(endDate);

        if (sessionFee == 0) {
            return sessionBuilder.sessionType(SessionType.FREE).build();
        }
        return sessionBuilder.sessionType(SessionType.PAID).sessionFee(sessionFee).build();
    }

    public static SessionEntity toEntity(Session session) {
        return new SessionEntity(session.getId(), session.getCoverFilePath(), session.getSessionState(),
                session.getEnrollment(), session.getMaxEnrollment(), session.getSessionFee(),
                session.getStartDate(), session.getEndDate());
    }

    public final boolean isPersisted() {
        return id != null && !id.equals((long) NOT_ASSIGNED);
    }
}
