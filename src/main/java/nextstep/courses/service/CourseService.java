package nextstep.courses.service;

import nextstep.courses.domain.course.Course;
import nextstep.courses.domain.course.CourseRepository;
import nextstep.courses.domain.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final SessionService sessionService;

    public CourseService(CourseRepository courseRepository, SessionService sessionService) {
        this.courseRepository = courseRepository;
        this.sessionService = sessionService;
    }

    @Transactional
    public long create(Course course, Session session) {
        long courseId = courseRepository.save(course);
        sessionService.create(courseId, session, session.getImage());

        return courseId;
    }

    @Transactional(readOnly = true)
    public Course findById(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        List<Session> sessions = sessionService.findAllByCourseId(course.getId());

        return Course.of(course, sessions);
    }
}
