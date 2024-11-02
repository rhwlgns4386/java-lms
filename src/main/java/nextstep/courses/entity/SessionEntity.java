package nextstep.courses.entity;

import lombok.Getter;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionBuilder;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import nextstep.courses.type.SessionType;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static nextstep.courses.domain.session.Session.NOT_ASSIGNED;

@Getter
public class SessionEntity {

    private Long id;
    private final String coverFilePath;
    private final SessionState sessionState;
    private final RecruitState recruitState;
    private final int enrollment;
    private final int maxEnrollment;
    private final long sessionFee;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public SessionEntity(Long id, String coverFilePath, SessionState sessionState, RecruitState recruitState,
                         int enrollment, int maxEnrollment, long sessionFee, Timestamp startDate, Timestamp endDate) {
        this(id, coverFilePath, sessionState, recruitState, enrollment, maxEnrollment,
                sessionFee, toLocalDateTime(startDate), toLocalDateTime(endDate));
    }

    public SessionEntity(String coverFilePath, SessionState sessionState, RecruitState recruitState, int enrollment,
                         int maxEnrollment, long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        this(null, coverFilePath, sessionState, recruitState, enrollment, maxEnrollment, sessionFee, startDate, endDate);
    }

    public SessionEntity(Long id, String coverFilePath, SessionState sessionState, RecruitState recruitState,
                         int enrollment, int maxEnrollment, long sessionFee,
                         LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.coverFilePath = coverFilePath;
        this.sessionState = sessionState;
        this.recruitState = recruitState;
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
                .recruitState(recruitState)
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

    public static SessionEntity from(Session session) {
        return new SessionEntity(session.getId(), session.getCoverFilePath(), session.getSessionState(),
                session.getRecruitState(), session.getEnrollment(), session.getMaxEnrollment(),
                session.getSessionFee(), session.getStartDate(), session.getEndDate());
    }

    public final boolean isPersisted() {
        return id != null && !id.equals((long) NOT_ASSIGNED);
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "id=" + id +
                ", coverFilePath='" + coverFilePath + '\'' +
                ", sessionState=" + sessionState +
                ", recruitState=" + recruitState +
                ", enrollment=" + enrollment +
                ", maxEnrollment=" + maxEnrollment +
                ", sessionFee=" + sessionFee +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
