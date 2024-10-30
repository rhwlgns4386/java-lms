package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.sessions.Session;
import nextstep.users.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course create(String title, Long creatorId, int cohort) {
        Course course = new Course(title, creatorId, cohort);
        courseRepository.save(course);
        return course;
    }

    public Course addSessionToCourse(Long courseId, Session session) {
        Course course = courseRepository.findById(courseId);
        course.addSession(session);
        return course;
    }

    public void processPayment(Long courseId, Student student, Long amount, Session session) {
        Course course = courseRepository.findById(courseId);
        course.processPayment(student, amount, session);
    }
}
