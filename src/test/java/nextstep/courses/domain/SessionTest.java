package nextstep.courses.domain;

import nextstep.fixture.FreeSessionBuilder;
import nextstep.fixture.FreeSessionCreator;
import nextstep.fixture.PaymentCreator;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static nextstep.courses.domain.SessionSelection.ALL_SELECT;
import static nextstep.courses.domain.SessionSelection.ALL_UNSELECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {

    @Test
    @DisplayName("선발전략을 별도로 세팅하지 않은 경우, 정상적으로 강의신청이 가능하다.")
    void 수강신청_선발_세팅안함() {
        Session session = new FreeSessionBuilder()
                .withRecruitingStatus(RecruitingStatus.RECRUITING)
                .withApplyStudents(new ArrayList<>())
                .build();
        session.enroll(PaymentCreator.pay(0L));
    }

    @Test
    @DisplayName("수강신청에서 선발된 경우, 정상적으로 강의신청이 가능하다.")
    void 수강신청_선발() {
        Session session = new FreeSessionBuilder()
                .withRecruitingStatus(RecruitingStatus.RECRUITING)
                .withSessionSelection(ALL_SELECT)
                .withApplyStudents(new ArrayList<>())
                .build();
        session.enroll(PaymentCreator.pay(0L));
    }


    @Test
    @DisplayName("수강 신청에서 선발되지 않을 경우, 예외를 발생시킨다.")
    void 수강신청_선발_실패() {
        Session session = new FreeSessionBuilder()
                .withRecruitingStatus(RecruitingStatus.RECRUITING)
                .withSessionSelection(ALL_UNSELECT)
                .withApplyStudents(new ArrayList<>())
                .build();
        assertThatThrownBy(() -> session.enroll(PaymentCreator.pay(0L)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("수강신청 하지 않은 유저를 취소하려고 하는 경우, 예외를 발생시킨다.")
    void 수강신청_cancel_없는유저() {
        Session session = new FreeSessionBuilder()
                .withRecruitingStatus(RecruitingStatus.RECRUITING)
                .withApplyStudents(List.of(NsUserTest.JAVAJIGI.getId()))
                .build();
        assertThatThrownBy(() -> session.cancel(NsUserTest.SANJIGI.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 유저를 수강신청 취소한다.")
    void 수강신청_cancel() {
        Session session = new FreeSessionBuilder()
                .withRecruitingStatus(RecruitingStatus.RECRUITING)
                .withApplyStudents(List.of(NsUserTest.JAVAJIGI.getId()))
                .build();
        session.cancel(NsUserTest.JAVAJIGI.getId());
        Assertions.assertAll(
                () -> assertThat(session.getApplyStudents()).isEqualTo(new ArrayList<>()),
                () -> assertThat(session.getApprovedStudents()).isEqualTo(new ArrayList<>())
        );
    }

    @Test
    @DisplayName("수강신청 하지 않은 유저를 승인하려고 하는 경우, 예외를 발생시킨다.")
    void 수강신청_approve_없는유저() {
        Session session = new FreeSessionBuilder()
                .withRecruitingStatus(RecruitingStatus.RECRUITING)
                .withApplyStudents(List.of(NsUserTest.JAVAJIGI.getId()))
                .build();
        assertThatThrownBy(() -> session.approve(NsUserTest.SANJIGI.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 유저를 수강신청 승인한다.")
    void 수강신청_approve() {
        Session session = new FreeSessionBuilder()
                .withRecruitingStatus(RecruitingStatus.RECRUITING)
                .withApplyStudents(List.of(NsUserTest.JAVAJIGI.getId()))
                .build();
        session.approve(NsUserTest.JAVAJIGI.getId());
        Assertions.assertAll(
                () -> assertThat(session.getApplyStudents()).isEqualTo(new ArrayList<>()),
                () -> assertThat(session.getApprovedStudents()).isEqualTo(List.of(1L))
        );
    }

    @Test
    @DisplayName("수강신청을 하면 applyStudent에 추가된다.")
    void 수강신청_apply() {
        Session session = new FreeSessionBuilder()
                .withRecruitingStatus(RecruitingStatus.RECRUITING)
                .withApplyStudents(new ArrayList<>())
                .build();
        session.enroll(PaymentCreator.user(1L));
        assertThat(session.getApplyStudents()).isEqualTo(List.of(1L));
    }

    @Test
    @DisplayName("유저가 포함되지 않았으면 false를 반환한다.")
    void 유저_불포함() {
        Session session = FreeSessionCreator.user(2L);
        assertThat(session.isContainUser(NsUserTest.JAVAJIGI)).isFalse();
    }

    @Test
    @DisplayName("유저가 포함되어 있으면 true를 반환한다.")
    void 유저_포함() {
        Session session = FreeSessionCreator.user(2L);
        assertThat(session.isContainUser(NsUserTest.SANJIGI)).isTrue();
    }
}
