package nextstep.sessions.domain;

import nextstep.sessions.SessionDetail;
import nextstep.users.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("SessionDetail 도메인 테스트")
public class SessionDetailTest {

    private SessionDetail freeSessionDetail;
    private SessionDetail paidSessionDetail;
    private Student student;

    @BeforeEach
    public void setUp() {
        freeSessionDetail = new SessionDetail(true, 100);
        paidSessionDetail = new SessionDetail(false, 50, 300L);
        student = new Student(1L, "John Doe", "1234", "Doe", "JohnDoe@test.com");
    }

    @Test
    @DisplayName("무료 강의 생성 확인")
    public void 무료_강의_생성_테스트() {
        assertThat(freeSessionDetail.isSameAmount(0L)).isTrue();
        assertThat(freeSessionDetail.isAllowable()).isTrue();
    }

    @Test
    @DisplayName("유료 강의 생성 확인")
    public void 유료_강의_생성_테스트() {
        assertThat(paidSessionDetail.isSameAmount(300L)).isTrue();
        assertThat(paidSessionDetail.isAllowable()).isTrue();
    }

    @Test
    @DisplayName("학생 등록이 정상적으로 가능한지 확인")
    public void 학생_등록_테스트() {
        paidSessionDetail.checkRegistrationEligibility(300L);
        paidSessionDetail.registerNewStudent(student);

        assertThat(paidSessionDetail.isAllowable()).isTrue();
    }

    @Test
    @DisplayName("잘못된 강의료로 등록 시 예외 발생 확인")
    public void 잘못된_강의료로_등록시_예외_발생() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paidSessionDetail.checkRegistrationEligibility(100L); // 잘못된 금액
        });
        assertThat(exception.getMessage()).isEqualTo("수납해야할 강의료가 일치하지 않습니다. 강의료 : 300원");
    }

    @Test
    @DisplayName("최대 수강 인원 초과 시 예외 발생 확인")
    public void 최대_수강인원_초과시_예외_발생() {
        SessionDetail limitedSessionDetail = new SessionDetail(false, 1, 300L);
        limitedSessionDetail.checkRegistrationEligibility(300L);
        limitedSessionDetail.registerNewStudent(student);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            limitedSessionDetail.checkRegistrationEligibility(300L);
            limitedSessionDetail.registerNewStudent(new Student(2L, "Jane Doe", "1234", "Doe", "johndoe@test.com"));
        });

        assertThat(exception.getMessage()).isEqualTo(SessionDetail.SESSION_FULL_MESSAGE);
    }

    @Test
    @DisplayName("최소 및 최대 학생 수 미달 및 초과 시 예외 발생 확인")
    public void 최소_학생_수_미달_및_초과시_예외_발생() {
        assertThrows(IllegalArgumentException.class, () -> new SessionDetail(false, 0, 200L));
        assertThrows(IllegalArgumentException.class, () -> new SessionDetail(false, 1000, 200L));
    }
}
