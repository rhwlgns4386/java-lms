package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.sessions.Session;
import nextstep.users.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final PaymentService paymentService;

    @Autowired
    public CourseService(CourseRepository courseRepository, PaymentService paymentService) {
        this.courseRepository = courseRepository;
        this.paymentService = paymentService;
    }


    @Transactional
    public Course create(String title, Long creatorId, int cohort) {
        Course course = new Course(title, creatorId, cohort);
        courseRepository.save(course);
        return course;
    }

    @Transactional
    public Optional<Course> addSessionToCourse(Long courseId, Session session) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.addSession(session);
            courseRepository.save(course);
            return Optional.of(course);
        }

        return Optional.empty();
    }

    public void processPayment(Long courseId, Student student, Long amount, Session session) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isEmpty()) {
            throw new IllegalArgumentException("해당하는 강의가 없습니다.");
        }

        Payment payment = optionalCourse.get().processPayment(student, amount, session);
        paymentService.save(payment);

    }
}
