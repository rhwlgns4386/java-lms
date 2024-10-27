package nextstep.courses.domain.course;

import nextstep.courses.domain.session.Session;
import nextstep.courses.dto.SessionPaymentInfo;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private Long id;

    private String title;

    private Long creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private final List<Session> sessions = new ArrayList<>();

    public Course() {
    }

    public Course(String title, Long creatorId) {
        this(0L, title, creatorId, LocalDateTime.now(), null);
    }

    public Course(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void add(Session session) {
        sessions.add(session);
    }

    public boolean has(Session session) {
        return sessions.contains(session);
    }

    public SessionPaymentInfo tryRegister(Long sessionId) {
        return sessions.stream()
                .filter(session -> session.hasSameId(sessionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다"))
                .tryRegisterForSession();
    }

    public boolean finalizeRegistration(Payment payment) {
        return sessions.stream()
                .filter(session -> session.checkPaymentInfoMatch(payment))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결제 정보에 대한 강의를 찾을 수 없습니다"))
                .finalizeSessionRegistration(payment);
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creatorId=" + creatorId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
