package nextstep.courses.domain.course;

import nextstep.courses.domain.session.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private Long id;

    private Long order;

    private String title;

    private Long creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Session> sessions = new ArrayList<>();

    public Course() {
    }

    public Course(String title, Long creatorId) {
        this(0L, title, creatorId, LocalDateTime.now(), null);
    }

    public Course(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id, null, title, creatorId, createdAt, updatedAt);
    }

    public Course(Long id, Long order, String title, Long creatorId) {
        this(id, order, title, creatorId, LocalDateTime.now(), null);
    }

    public Course(Long id, Long order, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id, order, title, creatorId, createdAt, updatedAt, new ArrayList<>());
    }

    public Course(Long id, Long order, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt, List<Session> sessions) {
        this.id = id;
        this.order = order;
        this.title = title;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sessions = sessions;
    }

    public static Course of(Course course, List<Session> sessions) {
        return new Course(course.getId(), course.getOrder(), course.getTitle(), course.getCreatorId(), course.getCreatedAt(), course.getUpdatedAt(), sessions);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getOrder() {
        return order;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Session> getSessions() {
        return sessions;
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

    public void addSessions(Session... sessions) {
        this.sessions.addAll(List.of(sessions));
    }
}
