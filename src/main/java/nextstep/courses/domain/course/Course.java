package nextstep.courses.domain.course;

import nextstep.courses.domain.session.Session;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private Long id;

    private String title;

    private CreationInfo creationInfo;

    private final List<Session> sessions = new ArrayList<>();

    public Course() {
    }

    public Course(String title, Long creatorId) {
        this(0L, title, creatorId, LocalDateTime.now(), null);
    }

    public Course(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.creationInfo = new CreationInfo(creatorId, createdAt, updatedAt);
    }

    public void add(Session session) {
        sessions.add(session);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creationInfo.getCreatorId();
    }

    public LocalDateTime getCreatedAt() {
        return creationInfo.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return creationInfo.getUpdatedAt();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creationInfo=" + creationInfo +
                ", sessions=" + sessions +
                '}';
    }
}
