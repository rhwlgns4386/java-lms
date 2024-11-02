package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class SessionTest {

    public final static Session SESSION_REDAY = new Session("제목1", LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS),
            0, StateCode.READY, 1, 100, "jpg", 300, 200, "imageFileName1", true);

    public final static Session SESSION_RECRUITING = new Session("제목2", LocalDateTime.now(), LocalDateTime.now().plus(20, ChronoUnit.HALF_DAYS),
            0, StateCode.RECRUITING, 2, 200, "png", 600, 400, "imageFileName2", true);

    public final static Session SESSION_END = new Session("제목3", LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.HALF_DAYS),
            0, StateCode.END, 3, 300, "png", 1200, 800, "imageFileName3", true);


    @Test
    @DisplayName("강의는 모집중에만 등록이 가능 pass")
    void validateOrderSessionStatus() {
        SESSION_RECRUITING.validateOrderSessionStatus();
    }

    @Test
    @DisplayName("강의는 모집중이 아닌 경우 오류")
    void validateRegistSessionStatus_IllegalArgumentException() {
        assertThatThrownBy(() -> {
            SESSION_REDAY.validateOrderSessionStatus();
        }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> {
            SESSION_END.validateOrderSessionStatus();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("처음 강의를 신청하면 false")
    void isDuplicateStudent_false() {
        assertThat(SESSION_RECRUITING.isDuplicateStudent(NsUserTest.JAVAJIGI)).isFalse();
    }

    @Test
    @DisplayName("중복으로 강의를 신청하면 true")
    void isDuplicateStudent_true() {
        SESSION_RECRUITING.updateStudent(NsUserTest.SANJIGI);

        assertThat(SESSION_RECRUITING.isDuplicateStudent(NsUserTest.SANJIGI)).isTrue();
    }
}
