package nextstep.courses.domain.session;

import nextstep.courses.CannotApplyException;
import nextstep.courses.domain.session.enrollment.Enrollment;
import nextstep.courses.domain.session.enrollment.FreeEnrollment;
import nextstep.courses.domain.session.enrollment.Price;
import nextstep.courses.domain.session.enrollment.PriceTest;
import nextstep.courses.domain.session.enrollment.Status;
import nextstep.courses.domain.session.enrollment.Students;
import nextstep.courses.domain.session.sessionCoverImage.SessionCoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createHeight;
import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createRatio;
import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createSize;
import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createWidth;

class SessionTest {

    Session getFreeSession() {
        SessionCoverImage sessionCoverImage = new SessionCoverImage("jpg",
                                                                    createRatio(createWidth(300), createHeight(200)),
                                                                    createSize(1_048_575L));
        SessionInfo sessionInfo = new SessionInfo("강의1", sessionCoverImage, 1L);

        Students students = new Students(0);
        Price freePrice = PriceTest.createFreePrice();
        Enrollment freeEnrollment = new FreeEnrollment(Status.RECRUIT, students, freePrice);

        SessionPeriod sessionPeriod = new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(30L));

        return new Session(sessionInfo, freeEnrollment, sessionPeriod);
    }

    @Test
    void enroll_수강신청_성공케이스() {
        NsUser user1 = NsUserTest.JAVAJIGI;
        Payment payment1 = new Payment("1", 1L, 1L, 0L);

        NsUser user2 = NsUserTest.SANJIGI;
        Payment payment2 = new Payment("2", 2L, 2L, 0L);

        Session freeSession = getFreeSession();

        freeSession.enroll(user1, payment1);
        freeSession.enroll(user2, payment2);

        Assertions.assertThat(freeSession).isNotNull();
    }

    @Test
    @DisplayName("이미 등록이 완료된 학생인 경우 수강신청이 불가능하다")
    void enroll_수강신청_실패케이스() {
        NsUser user1 = NsUserTest.JAVAJIGI;
        Payment payment1 = new Payment("1", 1L, 1L, 0L);

        Session freeSession = getFreeSession();
        freeSession.enroll(user1, payment1);

        Assertions.assertThatThrownBy(() -> {
            freeSession.enroll(user1, payment1);
        }).isInstanceOf(CannotApplyException.class);
    }

}
