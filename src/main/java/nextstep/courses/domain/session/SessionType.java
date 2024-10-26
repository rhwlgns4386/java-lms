package nextstep.courses.domain.session;

public class SessionType {
    private final PriceType type;
    private final int maximumEnrollment;
    private final int tuition;

    private SessionType(PriceType type, int maximumEnrollment, int tuition) {
        this.type = type;
        this.maximumEnrollment = maximumEnrollment;
        this.tuition = tuition;
    }

    public static SessionType freeType() {
        return new SessionType(PriceType.FREE, Integer.MAX_VALUE, 0);
    }

    public static SessionType paidType(int maximumEnrollment, int tuition) {
        return new SessionType(PriceType.PAID, maximumEnrollment, tuition);
    }

    public boolean isFree() {
        return type == PriceType.FREE;
    }

    public int getMaximumEnrollment() {
        return maximumEnrollment;
    }

    public int getTuition() {
        return tuition;
    }

    public boolean isOverEnrollment(int enrollment) {
        return maximumEnrollment <= enrollment;
    }

}
