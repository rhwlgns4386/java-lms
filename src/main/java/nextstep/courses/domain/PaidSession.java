package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class PaidSession extends Session {
    private SessionCapacity capacity;
    private Money fee;
    private List<Registration> registrations;

    public PaidSession(Long id,
        String title,
        Image image,
        SessionDate sessionDate,
        SessionCapacity capacity,
        Money fee
    ) {
        super(image, sessionDate, id, title, SessionStatus.PREPARING, SessionType.PAID);
        this.capacity = capacity;
        this.fee = fee;
        this.registrations = new ArrayList<>();
    }

    public SessionCapacity getCapacity() {
        return capacity;
    }

    public Money getFee() {
        return fee;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    @Override
    protected void register(Registration registration) {
        if (!isAvailableForRegistration()) {
            throw new IllegalStateException("Can't register session");
        }
        if (registration == null) {
            throw new IllegalArgumentException("Registration must not be null");
        }
        if (!this.fee.equals(new Money(registration.getAmount()))) {
            throw new IllegalArgumentException("amount must be equal to session fee");
        }

        this.capacity.increase();
        this.registrations.add(registration);
    }
}
