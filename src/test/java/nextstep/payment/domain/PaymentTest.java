package nextstep.payment.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PaymentTest {

    @Test
    public void 결제_생성_및_정보_확인() {
        String id = "1";
        Long courseId = 100L;
        Long sessionId = 200L;
        Long studentId = 300L;
        Long amount = 15000L;

        Payment payment = new Payment(id, courseId, sessionId, studentId, amount);

        assertAll(
                () -> assertThat(payment).isNotNull(),
                () -> assertThat(payment.getId()).isEqualTo(id),
                () -> assertThat(payment.getCourseId()).isEqualTo(courseId),
                () -> assertThat(payment.getSessionId()).isEqualTo(sessionId),
                () -> assertThat(payment.getStudentId()).isEqualTo(studentId),
                () -> assertThat(payment.getAmount()).isEqualTo(amount),
                () -> assertThat(payment.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now())
        );


    }
}
