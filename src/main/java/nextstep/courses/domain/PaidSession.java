package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.util.Objects;

public class PaidSession extends Session {
    private final Long fee;

    public PaidSession(String title, LocalDate startDay, LocalDate endDay, int maxCapacity, Long fee, SessionImage sessionImage) {
        this(title, startDay, endDay, SessionStatus.READY, new Students(maxCapacity), fee, sessionImage);
    }

    public PaidSession(String title, LocalDate startDay, LocalDate endDay, SessionStatus sessionStatus, int maxCapacity, Long fee, SessionImage sessionImage) {
        this(title, startDay, endDay, sessionStatus, new Students(maxCapacity), fee, sessionImage);
    }

    public PaidSession(String title, LocalDate startDay, LocalDate endDay, SessionStatus sessionStatus, Students students, Long fee, SessionImage sessionImage) {
        this(0L, title, startDay, endDay, sessionStatus, students, fee, sessionImage);
    }

    public PaidSession(Long id, String title, LocalDate startDay, LocalDate endDay, SessionStatus sessionStatus, Students students, Long fee, SessionImage sessionImage) {
        super(id, title, startDay, endDay, sessionStatus, students, sessionImage);
        this.fee = fee;
    }

    @Override
    protected void validateRegistration(NsUser student, Payment payment) {
        payment.validateForSessionAndUser(getId(), student.getId(), getFee());
    }


    @Override
    public Long getFee() {
        return fee;
    }


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
