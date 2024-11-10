package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class SessionTest {

    public final static Session SESSION_RECRUITING_READY = new PaidSession(new SessionId(-1), SessionInfoTest.SESSION_INFO_RECRUIT_READY, SessionImagesTest.SESSION_IMAGE, new SessionPrice(2000), 10);
    public final static Session SESSION_RECRUITING_PRORESS = new PaidSession(new SessionId(-1), SessionInfoTest.SESSION_INFO_RECRUIT_PROGRESS, SessionImagesTest.SESSION_IMAGE, new SessionPrice(1000), 10);
    public final static Session SESSION_RECRUITING_END = new PaidSession(new SessionId(-1), SessionInfoTest.SESSION_INFO_RECRUIT_END, SessionImagesTest.SESSION_IMAGE, new SessionPrice(1000), 10);

    public final static Session SESSION_NO_RECRUITING_READY = new PaidSession(new SessionId(-1), SessionInfoTest.SESSION_INFO_NO_RECRUIT_READY, SessionImagesTest.SESSION_IMAGE, new SessionPrice(3000), 10);
    public final static Session SESSION_NO_RECRUITING_PROGRESS = new PaidSession(new SessionId(-1), SessionInfoTest.SESSION_INFO_NO_RECRUIT_PROGRESS, SessionImagesTest.SESSION_IMAGE, new SessionPrice(3000), 10);
    public final static Session SESSION_NO_RECRUITING_END = new PaidSession(new SessionId(-1), SessionInfoTest.SESSION_INFO_NO_RECRUIT_END, SessionImagesTest.SESSION_IMAGE, new SessionPrice(3000), 10);


    @Test
    @DisplayName("강의상태체크 호출하여 강의가 모집중이면 수강신청이 가능 pass")
    void validateOrderSessionStatus() {
        SESSION_RECRUITING_PRORESS.validateOrderSessionStatus();
        SESSION_RECRUITING_READY.validateOrderSessionStatus();
    }

    @Test
    @DisplayName("강의상태체크 호출하여 강의가 모집중이 아닌 경우 오류")
    void validateRegistSessionStatus_IllegalArgumentException() {
        assertThatThrownBy(() -> {
            SESSION_NO_RECRUITING_READY.validateOrderSessionStatus();
            SESSION_NO_RECRUITING_PROGRESS.validateOrderSessionStatus();
            SESSION_NO_RECRUITING_END.validateOrderSessionStatus();
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("모집하지 않는 강의입니다.");
    }

    @Test
    @DisplayName("강의진행상태체크 호출하여 강의가 종료된 경우 모집중이어도 오류")
    void validateOrderSessionProgressCode_IllegalArgumentException() {
        assertThatThrownBy(() -> {
            SESSION_RECRUITING_END.validateOrderSessionProgressCode();
            SESSION_NO_RECRUITING_END.validateOrderSessionProgressCode();
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("종료된 강의입니다.");
    }

    @Test
    @DisplayName("처음 강의를 신청하면 false")
    void isDuplicateStudent_false() {
        assertThat(SESSION_RECRUITING_PRORESS.isDuplicateStudent(NsUserTest.JAVAJIGI)).isFalse();
    }

    @Test
    @DisplayName("중복으로 강의를 신청하면 true")
    void isDuplicateStudent_true() {
        SESSION_RECRUITING_PRORESS.updateStudent(NsUserTest.SANJIGI);

        assertThat(SESSION_RECRUITING_PRORESS.isDuplicateStudent(NsUserTest.SANJIGI)).isTrue();
    }
}
