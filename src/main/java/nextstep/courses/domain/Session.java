package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {
    private Long id;

    private SessionImage image;

    private SessionType type;

    private int studentCount;

    private int maxStudentCount;

    private Long price;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public Session(Long id, SessionImage image, SessionType type, int maxStudentCount,
                   Long price, LocalDateTime startedAt, LocalDateTime endedAt) {
        validateSession(type, maxStudentCount, price);

        this.id = id;
        this.image = image;
        this.type = type;
        this.studentCount = 0;
        this.maxStudentCount = maxStudentCount;
        this.price = price;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    private void validateSession(SessionType type, int maxStudentCount, Long price) {
        if (type == SessionType.PAID) {
            validatePaidSession(maxStudentCount, price);
        }
        if (type == SessionType.FREE) {
            validateFreeSession(maxStudentCount, price);
        }
    }

    private void validatePaidSession(int maxStudentCount, Long price) {
        if (maxStudentCount <= 0) {
            throw new IllegalArgumentException("유료 강의는 최대 수강 인원을 설정해야 합니다.");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("유료 강의는 수강료를 설정해야 합니다.");
        }
    }

    private void validateFreeSession(int maxStudentCount, Long price) {
        if (maxStudentCount > 0) {
            throw new IllegalArgumentException("무료 강의는 최대 수강 인원 제한을 설정할 수 없습니다.");
        }
        if (price != null && price > 0) {
            throw new IllegalArgumentException("무료 강의는 수강료를 설정할 수 없습니다.");
        }
    }

    public void enroll(Payment payment) {
        validateEnrollment(payment);
        studentCount++;
    }

    private void validateEnrollment(Payment payment) {
        if (type == SessionType.PAID) {
            validatePaidSessionEnrollment(payment);
        }
    }

    private void validatePaidSessionEnrollment(Payment payment) {
        if (studentCount >= maxStudentCount) {
            throw new IllegalStateException("수강 인원이 초과되었습니다.");
        }
        if (!Objects.equals(payment.getAmount(), price)) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다.");
        }
    }


}

