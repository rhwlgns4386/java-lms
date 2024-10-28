package nextstep.courses.service;

import nextstep.courses.domain.course.Course;
import nextstep.courses.dto.SessionPaymentInfo;
import nextstep.courses.infrastructure.CourseRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final PaymentService paymentService;

    public CourseService(CourseRepository courseRepository, PaymentService paymentService) {
        this.courseRepository = courseRepository;
        this.paymentService = paymentService;
    }

    public Payment registerSession(Long courseId, Long sessionId, NsUser courseApplicant) {
        Course foundCourse = Optional.ofNullable(courseRepository.findById(courseId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의 기수를 찾을 수 없습니다"));
        SessionPaymentInfo sessionPaymentInfo = foundCourse.preCheckForRegister(sessionId);
        Payment payment = paymentService.payment("결제 진행 아이디", courseApplicant.getId(), sessionPaymentInfo);
        foundCourse.finalizeRegistration(payment);
        return payment;
    }
}
