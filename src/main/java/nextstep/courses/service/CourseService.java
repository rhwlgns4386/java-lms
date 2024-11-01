package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sessionEnrollmentService")
public class CourseService {

    @Resource(name = "courseRepository")
    private CourseRepository courseRepository;

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    public Course getCourse(Long id) {
        Course course = courseRepository.findById(id);
        List<Session> sessionList = sessionRepository.findByCourseId(id);
        course.addSessions(sessionList);
        return course;
    }
}
