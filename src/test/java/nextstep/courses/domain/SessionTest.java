package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class SessionTest {

    public final static SessionInfo SESSION_INFO_RECRUIT = new SessionInfo(new SessionMetaData("모집중_진행중","작성자1"),
                                                                   new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
                                                                   StateCode.RECRUITING);

    public final static SessionInfo SESSION_INFO_NO_RECRUIT = new SessionInfo(new SessionMetaData("비모집_진행중", "createorId"),
                                                                       new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
                                                                       StateCode.NO_RECRUITING);

    public final static SessionImage SESSION_IMAGE = new SessionImage(200, "png", 600, 400, "imageFileName");

    public final static Session SESSION_RECRUITING = new Session(SESSION_INFO_RECRUIT, SESSION_IMAGE, new SessionPrice(1000), SessionType.PAID);
    public final static Session SESSION_NO = new Session(SESSION_INFO_NO_RECRUIT, SESSION_IMAGE, new SessionPrice(3000), SessionType.PAID);


    @Test
    @DisplayName("강의는 모집중에만 등록이 가능 pass")
    void validateOrderSessionStatus() {
       // SESSION_RECRUITING.validateOrderSessionStatus();
    }

    @Test
    @DisplayName("강의는 모집중이 아닌 경우 오류")
    void validateRegistSessionStatus_IllegalArgumentException() {
        assertThatThrownBy(() -> {
            SESSION_INFO_NO_RECRUIT.validateOrderSessionStatus();
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
