package nextstep.courses.domain.session.entity;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionInfo;
import nextstep.courses.domain.session.SessionPeriod;
import nextstep.courses.domain.session.enrollment.FreeEnrollment;
import nextstep.courses.domain.session.enrollment.PayEnrollment;
import nextstep.courses.domain.session.enrollment.PayType;
import nextstep.courses.domain.session.enrollment.Price;
import nextstep.courses.domain.session.enrollment.Status;
import nextstep.courses.domain.session.enrollment.Students;
import nextstep.courses.domain.session.sessioncoverimage.SessionCoverImage;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.List;

public class SessionEntity {
    private final Long id;

    private final String title;

    private final Long creatorId;

    private final String status;

    private final long price;

    private final String payType;

    private final int maxStudentCount;

    private final long coverImageId;

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public SessionEntity(String title,
                         Long creatorId,
                         String status,
                         long price,
                         String payType,
                         int maxStudentCount,
                         long coverImageId,
                         LocalDateTime startDateTime,
                         LocalDateTime endDateTime) {
        this(0L, title, creatorId, status, price, payType, maxStudentCount, coverImageId, startDateTime, endDateTime);
    }

    public SessionEntity(Long id,
                         String title,
                         Long creatorId,
                         String status,
                         long price,
                         String payType,
                         int maxStudentCount,
                         long coverImageId,
                         LocalDateTime startDateTime,
                         LocalDateTime endDateTime) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.status = status;
        this.price = price;
        this.payType = payType;
        this.maxStudentCount = maxStudentCount;
        this.coverImageId = coverImageId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getStatus() {
        return status;
    }

    public long getPrice() {
        return price;
    }

    public String getPayType() {
        return payType;
    }

    public int getMaxStudentCount() {
        return maxStudentCount;
    }

    public long getCoverImageId() {
        return coverImageId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", creatorId=" + creatorId +
            ", status='" + status + '\'' +
            ", price=" + price +
            ", payType='" + payType + '\'' +
            ", maxStudentCount=" + maxStudentCount +
            ", coverImageId='" + coverImageId + '\'' +
            ", startDateTime=" + startDateTime +
            ", endDateTime=" + endDateTime +
            '}';
    }

    public Session toSession(SessionCoverImage sessionCoverImage, List<NsUser> students) {
        if (PayType.isPay(payType)) {
            return new Session(
                new SessionInfo(title, sessionCoverImage, creatorId),
                new PayEnrollment(Status.valueOf(status),
                                  new Students(maxStudentCount, students),
                                  new Price(price, PayType.valueOf(payType))),
                new SessionPeriod(startDateTime, endDateTime));
        }

        return new Session(
            new SessionInfo(title, sessionCoverImage, creatorId),
            new FreeEnrollment(Status.valueOf(status), new Students(maxStudentCount, students)),
            new SessionPeriod(startDateTime, endDateTime));
    }
}
