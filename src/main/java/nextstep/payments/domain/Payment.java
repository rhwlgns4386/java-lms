package nextstep.payments.domain;

import java.time.LocalDateTime;

public class Payment {
    private String id;
    private Long courseId;
    private Long sessionId;
    private Long studentId;
    private Long amount;
    private LocalDateTime createdAt;

    public Payment(String id, Long courseId, Long sessionId, Long studentId, Long amount) {
        this.id = id;
        this.courseId = courseId;
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public void pay() {
    }

    public String getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getStudentId() {
        return studentId;
    }
}
