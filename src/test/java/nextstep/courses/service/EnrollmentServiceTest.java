package nextstep.courses.service;

import nextstep.courses.domain.*;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class EnrollmentServiceTest {

    @Autowired
    private EnrollmentService service;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    SessionImageRepository sessionImageRepository;

    @Autowired
    SessionUsersRepository sessionUsersRepository;

    @DisplayName("수강 신청 성공하면 수강 인원 목록에 유저가 포함된다")
    @Test
    void enrollSession() {
        Session session = createSessionWith(new SessionStudents());
        EnrollRequest request = new EnrollRequest(NsUserTest.SANJIGI, session, 10000);

        service.enrollSession(request);

        SessionStudents students = sessionUsersRepository.findBySessionId(10000L);
        assertThat(students.size()).isEqualTo(1);
    }

    @DisplayName("수강신청 후, 선발된 학생은 수강 가능하고 선발 안된 학생은 수강 인원 목록에서 제거된다")
    @Test
    void filterToSelected() {
        List<SessionStudent> studentGroup = new ArrayList<>();
        studentGroup.add(new SessionStudent(10000L, NsUserTest.JAVAJIGI.getId(), true));
        studentGroup.add(new SessionStudent(10000L, NsUserTest.SANJIGI.getId(), false));
        Session session = createSessionWith(new SessionStudents(studentGroup));

        service.filterToSelected(session);

        assertThat(session.getStudents().size()).isEqualTo(1);
    }

    private Session createSessionWith(SessionStudents students) {
        SessionPeriod dateRange = new SessionPeriod(
                LocalDateTime.of(2024, 11, 1, 0, 0),
                LocalDateTime.of(2024, 11, 30, 23, 59)
        );
        return new Session(
                10000L,
                10000L,
                dateRange,
                new Status(ProgressStatus.PREPARING, RecruitmentStatus.RECRUITING),
                new SessionImage(1L, "/image.png", 100, new Size(300, 200), 1L, LocalDateTime.now(), LocalDateTime.now()),
                new Pricing(PricingType.PAID, 10000),
                students,
                50,
                1L,
                LocalDateTime.now()
        );
    }
}
