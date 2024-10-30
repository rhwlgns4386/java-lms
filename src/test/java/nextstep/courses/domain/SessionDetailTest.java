package nextstep.courses.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void 무료_강의_생성_테스트() {
        assertThat(freeSessionDetail.isSameAmount(0L)).isTrue();
        assertThat(freeSessionDetail.isAllowable()).isTrue();
    }

    @Test
    public void 유료_강의_생성_테스트() {
        assertThat(paidSessionDetail.isSameAmount(300L)).isTrue();
        assertThat(paidSessionDetail.isAllowable()).isTrue();
    }

    @Test
    public void 학생_등록_테스트() {
        paidSessionDetail.registerNewStudent(student, 300L);
        assertThat(paidSessionDetail.isAllowable()).isTrue();
    }

    @Test
    public void 잘못된_강의료로_등록시_예외_발생() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paidSessionDetail.registerNewStudent(student, 200L);
        });
        assertThat(exception.getMessage()).isEqualTo("수납해야할 강의료가 일치하지 않습니다. 강의료 : 300원");
    }

    @Test
    public void 최대_수강인원_초과시_예외_발생() {
        SessionDetail limitedSessionDetail = new SessionDetail(false, 1, 300L);
        limitedSessionDetail.registerNewStudent(student, 300L);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            limitedSessionDetail.registerNewStudent(new Student(2L, "JaneDoe1234", "1234", "John Doe", "johndoe@test.com"), 300L);
        });
        assertThat(exception.getMessage()).isEqualTo("해당 강의는 수강인원이 모두 찼습니다.");
    }

    @Test
    public void 최소_학생_수_미달_및_초과시_예외_발생() {
        assertThrows(IllegalArgumentException.class, () -> new SessionDetail(false, 0, 200L));
        assertThrows(IllegalArgumentException.class, () -> new SessionDetail(false, 1000, 200L));
    }
}
