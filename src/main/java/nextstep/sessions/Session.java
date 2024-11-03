package nextstep.sessions;

import nextstep.sessions.Image.CoverImage;
import nextstep.users.domain.Student;

import java.time.LocalDateTime;

public class Session {
    public static final String SESSION_NOT_OPEN_MESSAGE = "아직 접수 기간이 아닙니다.";

    private static final int MAX_STUDENT_COUNT = 999;
    private static final boolean DEFAULT_IS_FREE = true;
    private static final Long DEFAULT_SESSION_FEE = 0L;
    private final Long id;
    private final String name;
    private final String description;
    private SessionState state;
    private final CoverImage image;

    private final SessionDetail sessionDetail;
    private final SessionDate sessionDate;

    private Session(SessionBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.image = builder.image;
        this.state = builder.state != null ? builder.state : SessionState.READY;
        this.sessionDate = new SessionDate(builder.startDate, builder.endDate);
        this.sessionDetail = new SessionDetail(
                builder.isFree,
                builder.isFree ? MAX_STUDENT_COUNT : builder.maxStudentCount,
                builder.sessionFee
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return sessionDate.getStartDate();
    }

    public LocalDateTime getEndDate() {
        return sessionDate.getEndDate();
    }

    public void register(Student student, Long amount) {
        validateRegistrationEligibility(amount);
        sessionDetail.registerNewStudent(student);
    }

    private void validateRegistrationEligibility(Long amount) {
        if (!state.isOpen()) {
            throw new IllegalStateException(SESSION_NOT_OPEN_MESSAGE);
        }

        sessionDetail.checkRegistrationEligibility(amount);
    }

    public void setClose() {
        this.state = SessionState.CLOSE;
    }

    public boolean contains(Student student) {
        return sessionDetail.contains(student);
    }

    public static class SessionBuilder {
        private final Long id;
        private final String name;
        private final String description;
        private final CoverImage image;
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
        private boolean isFree = DEFAULT_IS_FREE;
        private int maxStudentCount = MAX_STUDENT_COUNT;
        private Long sessionFee = DEFAULT_SESSION_FEE;
        private SessionState state;

        public SessionBuilder(Long id, String name, String description, CoverImage image, LocalDateTime startDate, LocalDateTime endDate) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.image = image;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public SessionBuilder isFree(boolean isFree) {
            this.isFree = isFree;
            return this;
        }

        public SessionBuilder maxStudentCount(int maxStudentCount) {
            this.maxStudentCount = maxStudentCount;
            return this;
        }

        public SessionBuilder sessionFee(Long sessionFee) {
            this.sessionFee = sessionFee;
            return this;
        }

        public SessionBuilder state(SessionState state) {
            this.state = state;
            return this;
        }

        public Session build() {
            return new Session(this);
        }
    }
}
