package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.sessions.Session;
import nextstep.sessions.SessionState;
import nextstep.users.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private Student student;
    private Session session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        course = new Course("자바지기", 1L, 1);
        student = new Student(1L, "test", "password", "test", "test@test.com");
        session = new Session.SessionBuilder(
                1L,
                "자바지기 1주차",
                "자바지기 1주차 내용",
                null,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2)
        ).state(SessionState.OPEN)
                .isFree(false)
                .maxStudentCount(30)
                .sessionFee(10000L)
                .build();
    }

    @Test
    @Transactional
    public void 코스_생성() {
        when(courseRepository.save(any(Course.class))).thenReturn(1);

        Course createdCourse = courseService.create("자바지기", 1L, 1);

        assertThat(createdCourse).isNotNull();
        assertThat(createdCourse).isEqualTo(course);
    }

    @Test
    @Transactional
    public void 코스에_세션을_추가() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        Optional<Course> updatedCourseOptional = courseService.addSessionToCourse(course.getId(), session);

        assertThat(updatedCourseOptional).isPresent();
        assertThat(updatedCourseOptional.get().getSessions().size()).isGreaterThan(0);
    }

    @Test
    public void 세션_결제를_요청() {
        course.addSession(session);

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        courseService.processPayment(course.getId(), student, 10000L, session);

        verify(paymentService, times(1)).save(any(Payment.class));
    }

    @Test
    public void 코스를_찾을_수_없을_경우_예외_발생() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            courseService.processPayment(course.getId(), student, 100L, session);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 강의가 없습니다.");
    }
}
