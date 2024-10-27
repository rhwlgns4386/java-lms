package nextstep.sessions.domain;

public class SessionType {
    private final PriceType type;
    private final MaximumEnrollment maximumEnrollment;
    private final int tuition;

    private SessionType(PriceType type, MaximumEnrollment maximumEnrollment, int tuition) {
        this.type = type;
        this.maximumEnrollment = maximumEnrollment;
        this.tuition = tuition;
    }

    public static SessionType freeType() {
        return new SessionType(PriceType.FREE, new MaximumEnrollment(Integer.MAX_VALUE), 0);
    }

    public static SessionType paidType(int maximumEnrollment, int tuition) {
        return new SessionType(PriceType.PAID, new MaximumEnrollment(maximumEnrollment), tuition);
    }

    public boolean isFree() {
        return type == PriceType.FREE;
    }

    public int getTuition() {
        return tuition;
    }

    public boolean isOverEnrollment(int enrollment) {
        return maximumEnrollment.isOver(enrollment);
    }

}
