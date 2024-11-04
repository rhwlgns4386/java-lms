package nextstep.courses.domain.session;

import nextstep.users.domain.NsUserTest;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class SessionRegistrationsTest {
    private SessionRegistrations registrations;

    @DisplayName("수강 신청하면 PENDING 상태로 등록된다")
    @Test
    void register() {
        registrations = new SessionRegistrations(1L, 2);
        registrations.register(NsUserTest.GREEN);

        assertThat(registrations.getPendingRegistrations())
                .hasSize(1)
                .extracting("userId", "state.registrationStatus", "state.selectionStatus")
                .containsExactly(
                        Tuple.tuple(NsUserTest.GREEN.getId(), SessionRegistrationStatus.PENDING, StudentSelectionStatus.PENDING)
                );
    }

    @DisplayName("최대 수강 인원을 초과하여 수강신청할 수 없다")
    @Test
    void registerOverMaxStudents() {
        // given
        registrations = new SessionRegistrations(1L, 2);
        registrations.register(NsUserTest.GREEN);
        registrations.select(NsUserTest.GREEN.getId());
        registrations.approve(NsUserTest.GREEN.getId());

        registrations.register(NsUserTest.JAVAJIGI);
        registrations.select(NsUserTest.JAVAJIGI.getId());
        registrations.approve(NsUserTest.JAVAJIGI.getId());

        // when & then
        assertThatThrownBy(() -> registrations.register(NsUserTest.SANJIGI))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수강인원이 꽉 찼습니다.");
    }

    @DisplayName("이미 수강신청한 사용자는 다시 신청할 수 없다")
    @Test
    void registerDuplicate() {
        // given
        registrations = new SessionRegistrations(1L, 2);
        registrations.register(NsUserTest.GREEN);

        // when & then
        assertThatThrownBy(() -> registrations.register(NsUserTest.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 수강신청한 사용자입니다.");
    }

    @DisplayName("수강신청을 선발하면 SELECTED 상태가 된다")
    @Test
    void select() {
        // given
        registrations = new SessionRegistrations(1L, 2);
        registrations.register(NsUserTest.GREEN);

        // when
        registrations.select(NsUserTest.GREEN.getId());

        // then
        assertThat(registrations.getSelectedRegistrations())
                .hasSize(1)
                .extracting("userId", "state.registrationStatus", "state.selectionStatus")
                .containsExactly(
                        Tuple.tuple(NsUserTest.GREEN.getId(), SessionRegistrationStatus.PENDING, StudentSelectionStatus.SELECTED)
                );
    }

    @DisplayName("선발된 수강신청만 승인할 수 있다")
    @Test
    void approve() {
        // given
        registrations = new SessionRegistrations(1L, 2);
        registrations.register(NsUserTest.GREEN);
        registrations.select(NsUserTest.GREEN.getId());

        // when
        registrations.approve(NsUserTest.GREEN.getId());

        // then
        assertThat(registrations.getApprovedRegistrations())
                .hasSize(1)
                .extracting("userId", "state.registrationStatus", "state.selectionStatus")
                .containsExactly(
                        Tuple.tuple(NsUserTest.GREEN.getId(), SessionRegistrationStatus.APPROVED, StudentSelectionStatus.SELECTED)
                );
    }

    @DisplayName("선발되지 않은 수강신청은 승인할 수 없다")
    @Test
    void approveNotSelected() {
        // given
        registrations = new SessionRegistrations(1L, 2);
        registrations.register(NsUserTest.GREEN);

        // when & then
        assertThatThrownBy(() -> registrations.approve(NsUserTest.GREEN.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("선발되지 않은 학생은 승인할 수 없습니다.");
    }

    @DisplayName("선발된 수강신청은 취소할 수 없다")
    @Test
    void cancelSelected() {
        // given
        registrations = new SessionRegistrations(1L, 2);
        registrations.register(NsUserTest.GREEN);
        registrations.select(NsUserTest.GREEN.getId());

        // when & then
        assertThatThrownBy(() -> registrations.cancel(NsUserTest.GREEN.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("선발된 학생은 취소할 수 없습니다.");
    }

    @DisplayName("선발되지 않은 수강신청은 취소할 수 있다")
    @Test
    void cancel() {
        // given
        registrations = new SessionRegistrations(1L, 2);
        registrations.register(NsUserTest.GREEN);
        registrations.reject(NsUserTest.GREEN.getId());

        // when
        registrations.cancel(NsUserTest.GREEN.getId());

        // then
        assertAll(
                () -> assertThat(registrations.getSelectedRegistrations()).isEmpty(),
                () -> assertThat(registrations.getPendingRegistrations()).isEmpty(),
                () -> assertThat(registrations.getApprovedRegistrations()).isEmpty()
        );
    }
}
