package nextstep.courses.domain.session;

import nextstep.courses.domain.enroll.EnrollUserInfo;
import nextstep.courses.domain.enroll.EnrollUserInfos;
import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.courses.domain.strategy.MockPaymentStrategy;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {

    private SessionCoverImage sessionCoverImage;
    private EnrollUserInfos enrollUserInfos;

    private Session freeSession;
    private Session paidSession;

    @BeforeEach
    void setUp() {
        sessionCoverImage = new SessionCoverImage.SessionCoverImageBuilder(1L).fileName("leo.png").filePath("/home/lms/image/cover/leo.png").volume(150).width(300).height(200).build();
        EnrollUserInfo enrollUserInfo = new EnrollUserInfo(1L, 1L);
        enrollUserInfos = new EnrollUserInfos(20);
        enrollUserInfos.add(enrollUserInfo);

        freeSession = new Session.SessionBuilder(1L)
                .price(1000L)
                .sessionPriceType(SessionPriceType.FREE)
                .sessionStatus(SessionStatus.ENROLLING)
                .sessionCoverImage(sessionCoverImage)
                .startDateTime(LocalDateTime.of(2024, 10, 30, 10, 30))
                .endDateTime(LocalDateTime.of(2024, 11, 20, 10, 30))
                .availableEnrollCount(30)
                .build();

        paidSession = new Session.SessionBuilder(2L)
                .price(1000L)
                .sessionPriceType(SessionPriceType.PAID)
                .sessionStatus(SessionStatus.ENROLLING)
                .sessionCoverImage(sessionCoverImage)
                .startDateTime(LocalDateTime.of(2024, 10, 30, 10, 30))
                .endDateTime(LocalDateTime.of(2024, 11, 20, 10, 30))
                .availableEnrollCount(30)
                .build();

    }

    @Test
    void 무료_강의_등록_성공_테스트() {
        freeSession.enroll(NsUserTest.JAVAJIGI, 0L, new MockPaymentStrategy());

        assertThat(freeSession.getEnrollUserInfos()).isEqualTo(Set.of(new EnrollUserInfo(1L, 1L)));
    }


    @Test
    void 유료_강의_등록_금액_성공_테스트() {
        paidSession.enroll(NsUserTest.JAVAJIGI, 1000L, new MockPaymentStrategy());

        assertThat(paidSession.getEnrollUserInfos()).isEqualTo(Set.of(new EnrollUserInfo(2L, 1L)));
    }

    @Test
    void 유료_강의_등록_금액_불일치_실패_테스트() {
        assertThatThrownBy(() -> paidSession.enroll(NsUserTest.JAVAJIGI, 900L, new MockPaymentStrategy()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("수강료와 지불 금액이 일치하지 않습니다.");
    }

    @Test
    void 유로_강의_등록_상태_PENDING_실패_테스트() {
        Session errorPaidSession = new Session.SessionBuilder(1L)
                .price(1000L)
                .sessionPriceType(SessionPriceType.PAID)
                .sessionStatus(SessionStatus.PENDING)
                .sessionCoverImage(sessionCoverImage)
                .startDateTime(LocalDateTime.of(2024, 10, 30, 10, 30))
                .endDateTime(LocalDateTime.of(2024, 11, 20, 10, 30))
                .availableEnrollCount(0)
                .build();

        assertThatThrownBy(() -> errorPaidSession.enroll(NsUserTest.JAVAJIGI, 0L, new MockPaymentStrategy()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("이 강의는 현재 모집중이 아닙니다.");
    }

    @Test
    void 유료_강의_등록_사이즈_실패_테스트() {
        Session errorPaidSession = new Session.SessionBuilder(1L)
                .price(1000L)
                .sessionPriceType(SessionPriceType.PAID)
                .sessionStatus(SessionStatus.ENROLLING)
                .sessionCoverImage(sessionCoverImage)
                .startDateTime(LocalDateTime.of(2024, 10, 30, 10, 30))
                .endDateTime(LocalDateTime.of(2024, 11, 20, 10, 30))
                .availableEnrollCount(0)
                .build();

        assertThatThrownBy(() -> errorPaidSession.enroll(NsUserTest.JAVAJIGI, 1000L, new MockPaymentStrategy()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("수강 정원이 모두 찼습니다.");
    }

}
