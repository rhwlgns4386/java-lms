package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionEntity {
    private final Long id;
    private final String status;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long coverImageId;
    private final String sessionType;
    private final Long courseFee;
    private final Integer maxStudents;
    private final List<Long> registeredUserIds;

    public SessionEntity(Long id, String status, LocalDate startDate, LocalDate endDate,
                         Long coverImageId, String sessionType, Long courseFee,
                         Integer maxStudents, List<Long> registeredUserIds) {
        this.id = id;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImageId = coverImageId;
        this.sessionType = sessionType;
        this.courseFee = courseFee;
        this.maxStudents = maxStudents;
        this.registeredUserIds = registeredUserIds;
    }

    public static SessionEntity fromPaidSession(PaidSession session) {
        return new SessionEntity(
                session.getId(),
                session.status.getCode(),
                session.period.getStartDate(),
                session.period.getEndDate(),
                session.coverImage.getId(),
                SessionType.PAID.getCode(),
                session.courseFee.getAmount(),
                session.capacity.getMaxStudentsSize(),
                session.capacity.getRegisteredStudentIds()
        );
    }

    public static SessionEntity fromFreeSession(FreeSession session) {
        return new SessionEntity(
                session.getId(),
                session.status.getCode(),
                session.period.getStartDate(),
                session.period.getEndDate(),
                session.coverImage.getId(),
                SessionType.FREE.getCode(),
                0L,
                Integer.MAX_VALUE,
                session.capacity.getRegisteredStudentIds()
        );
    }

    private PaidSession toPaidSessionDomain(CoverImage coverImage, Map<Long, NsUser> userMap) {
        Status sessionStatus = Status.valueOf(status);
        Period period = new Period(startDate, endDate);

        Set<NsUser> registeredUsers = registeredUserIds.stream()
                .map(userMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return new PaidSession(
                id,
                sessionStatus,
                period,
                coverImage,
                new Money(courseFee),
                new Capacity(registeredUsers, maxStudents)
        );
    }

    private FreeSession toFreeSessionDomain(CoverImage coverImage, Map<Long, NsUser> userMap) {
        Status sessionStatus = Status.valueOf(status);
        Period period = new Period(startDate, endDate);

        Set<NsUser> registeredUsers = registeredUserIds.stream()
                .map(userMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return new FreeSession(
                id,
                sessionStatus,
                period,
                coverImage,
                new Money(0L),
                new Capacity(registeredUsers, Integer.MAX_VALUE)
        );
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getCoverImageId() {
        return coverImageId;
    }

    public String getSessionType() {
        return sessionType;
    }

    public Long getCourseFee() {
        return courseFee;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public List<Long> getRegisteredUserIds() {
        return registeredUserIds;
    }
}
