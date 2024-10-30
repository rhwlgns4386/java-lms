package nextstep.courses.service;

import nextstep.courses.domain.course.Course;
import nextstep.courses.infrastructure.course.CourseRepository;
import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Payment registerSession(Long courseId, Payment payment) {
        Course foundCourse = Optional.ofNullable(courseRepository.findById(courseId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의 기수를 찾을 수 없습니다"));
        foundCourse.registerSession(payment);
        return payment;
    }
}
