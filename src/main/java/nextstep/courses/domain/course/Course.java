package nextstep.courses.domain.course;

import nextstep.courses.domain.session.DefaultSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private CourseMetadata courseMetadata;
    private Generation generation;
    private BaseTime baseTime;
    private List<DefaultSession> sessions;

    public Course(String title, Long creatorId) {
        this(0L, title, creatorId, LocalDateTime.now(), null);
    }

    public Course(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(
                new CourseMetadata(id, title, creatorId),
                new Generation(),
                new ArrayList<>(),
                new BaseTime(createdAt, updatedAt)
        );
    }

    public Course(CourseMetadata courseMetadata, Generation generation, List<DefaultSession> sessions, BaseTime baseTime) {
        this.courseMetadata = courseMetadata;
        this.generation = generation;
        this.sessions = sessions;
        this.baseTime = baseTime;
    }

    public Long getId() {
        return courseMetadata.getId();
    }

    public String getCourseTitle() {
        return courseMetadata.getTitle();
    }

    public long getCreatorId() {
        return courseMetadata.getCreatorId();
    }

    public LocalDateTime getCreatedAt() {
        return baseTime.getCreatedAt();
    }
}
