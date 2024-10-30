package nextstep.sessions;

import nextstep.sessions.Image.CoverImage;
import nextstep.users.domain.Student;

import java.time.LocalDateTime;

public class Session {
    private static final int MAX_STUDENT_COUNT = 999;
    private final Long id;
    private final String name;
    private final String description;
    private final CoverImage image;
    private final SessionState state;
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
        if (!state.isOpen()) {
            throw new IllegalStateException("아직 접수 기간이 아닙니다.");
        }
        sessionDetail.registerNewStudent(student, amount);
    }

    public static class SessionBuilder {
        private final Long id;
        private final String name;
        private final String description;
        private final CoverImage image;
        private final String startDate;
        private final String endDate;
        private boolean isFree = true;
        private int maxStudentCount = MAX_STUDENT_COUNT;
        private Long sessionFee = 0L;
        private SessionState state;

        public SessionBuilder(Long id, String name, String description, CoverImage image, String startDate, String endDate) {
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
