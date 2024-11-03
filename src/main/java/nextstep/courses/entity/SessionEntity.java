package nextstep.courses.entity;

import lombok.Getter;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionBuilder;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import nextstep.courses.type.SessionType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nextstep.courses.domain.session.Session.NOT_ASSIGNED;

@Getter
public class SessionEntity {

    private final Long id;
    private final String coverFilePath;
    private List<CoverImageEntity> coverImageEntities;
    private final SessionState sessionState;
    private final RecruitState recruitState;
    private final int enrollment;
    private final int maxEnrollment;
    private final long sessionFee;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final List<StudentEntity> studentEntities = new ArrayList<>();

    public SessionEntity(Long id, String coverFilePath, SessionState sessionState, RecruitState recruitState,
                         int enrollment, int maxEnrollment, long sessionFee, Timestamp startDate, Timestamp endDate) {
        this(id, coverFilePath, null, sessionState, recruitState, enrollment, maxEnrollment,
                sessionFee, toLocalDateTime(startDate), toLocalDateTime(endDate));
    }

    public SessionEntity(String coverFilePath, List<String> coverFilePaths, SessionState sessionState,
                         RecruitState recruitState, int enrollment, int maxEnrollment, long sessionFee,
                         LocalDateTime startDate, LocalDateTime endDate) {
        this(null, coverFilePath, coverFilePaths, sessionState, recruitState, enrollment, maxEnrollment,
                sessionFee, startDate, endDate);
    }

    public SessionEntity(Long id, String coverFilePath, List<String> coverFilePaths, SessionState sessionState,
                         RecruitState recruitState, int enrollment, int maxEnrollment, long sessionFee,
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
        this.coverImageEntities = Optional.ofNullable(coverFilePaths).orElse(new ArrayList<>())
                .stream()
                .map(CoverImageEntity::new)
                .collect(Collectors.toList());
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    public static SessionEntity from(Session session) {
        return new SessionEntity(session.getId(), session.getCoverFilePath(), session.getCoverFilePaths(),
                session.getSessionState(), session.getRecruitState(), session.getEnrollment(), session.getMaxEnrollment(),
                session.getSessionFee(), session.getStartDate(), session.getEndDate());
    }

    public Session toDomain() {
        SessionBuilder sessionBuilder = SessionBuilder.builder()
                .id(id)
                .sessionState(sessionState)
                .recruitState(recruitState)
                .coverImage(coverFilePath)
                .coverImages(coverImageEntities.stream().map(CoverImageEntity::toDomain).collect(Collectors.toList()))
                .enrollment(enrollment)
                .maxEnrollment(maxEnrollment)
                .students(studentEntities.stream().map(StudentEntity::toDomain).collect(Collectors.toList()))
                .startDate(startDate)
                .endDate(endDate);

        if (sessionFee == 0) {
            return sessionBuilder.sessionType(SessionType.FREE).build();
        }
        return sessionBuilder.sessionType(SessionType.PAID).sessionFee(sessionFee).build();
    }

    public void addCover(List<String> coverFilePaths) {
        this.coverImageEntities = coverFilePaths.stream().map(CoverImageEntity::new).collect(Collectors.toList());
    }

    public final boolean isPersisted() {
        return id != null && !id.equals((long) NOT_ASSIGNED);
    }

    public void addStudentEntities(List<StudentEntity> studentEntities) {
        this.studentEntities.addAll(studentEntities);
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
