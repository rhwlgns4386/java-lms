package nextstep.sessions.domain;

import nextstep.sessions.Image.CoverImage;
import nextstep.sessions.Session;
import nextstep.sessions.Session.SessionBuilder;
import nextstep.sessions.SessionState;
import nextstep.users.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {

    private Session session;
    private Student student;

    @BeforeEach
    public void setUp() {
        session = new SessionBuilder(1L, "테스트세션", "테스트 강의", null, "2024-01-01", "2024-01-02")
                .isFree(false)
                .maxStudentCount(50)
                .sessionFee(10000L)
                .state(SessionState.READY)
                .build();

        student = new Student(1L, "John Doe", "1234", "Doe", "johndoe@test.com");
    }

    @Test
    public void 강의_생성() {
        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getName()).isEqualTo("테스트세션");
        assertThat(session.getDescription()).isEqualTo("테스트 강의");
    }

    @Test
    public void 강의_날짜_확인() {
        assertThat(session.getStartDate()).isEqualTo(LocalDateTime.parse("2024-01-01T00:00:00"));
        assertThat(session.getEndDate()).isEqualTo(LocalDateTime.parse("2024-01-02T00:00:00"));
    }

    @Test
    public void 강의_등록_실패_접수_기간_아님() {
        session = new SessionBuilder(2L, "테스트세션", "테스트 강의", null, "2024-01-01", "2024-01-02")
                .state(SessionState.CLOSE) // 상태를 닫힘으로 설정
                .build();

        assertThatThrownBy(() -> session.register(student, 10000L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("아직 접수 기간이 아닙니다.");
    }

    @Test
    public void 유료_강의_설정() {
        Session paidSession = new SessionBuilder(3L, "유료세션", "유료 강의", null, "2024-05-01", "2024-05-10")
                .isFree(false)
                .sessionFee(50000L)
                .build();

        assertThat(paidSession.getDescription()).isEqualTo("유료 강의");
    }

    @Test
    public void 무료_강의_설정() {
        Session freeSession = new SessionBuilder(4L, "무료세션", "무료 강의", null, "2024-06-01", "2024-06-10")
                .isFree(true)
                .build();

        assertThat(freeSession.getDescription()).isEqualTo("무료 강의");
    }
}
