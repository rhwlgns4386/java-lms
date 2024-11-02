package nextstep.courses.service;

import nextstep.courses.domain.course.Course;
import nextstep.courses.domain.session.Session;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.infrastructure.course.CourseRepository;
import nextstep.courses.infrastructure.session.SessionRepository;
import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final SessionRepository sessionRepository;

    public CourseService(CourseRepository courseRepository, SessionRepository sessionRepository) {
        this.courseRepository = courseRepository;
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public void registerSessionEntity(Long courseId, Payment payment) {
        Course foundCourse = Optional.ofNullable(courseRepository.findById(courseId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의 기수를 찾을 수 없습니다"))
                .toDomain();
        sessionRepository.findByCourseId(foundCourse.getId()).forEach(entity -> foundCourse.add(entity.toDomain()));
        Session registeredSession = foundCourse.registerSession(payment);
        sessionRepository.save(SessionEntity.from(registeredSession), courseId);
    }
}
