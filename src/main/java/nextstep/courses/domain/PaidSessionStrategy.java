package nextstep.courses.domain;

import java.util.Objects;

public class PaidSessionStrategy implements SessionStrategy {


    private int studentCount = 0;
    private final int maxStudentCount;
    private final int price;

    public PaidSessionStrategy(int maxStudentCount, int price) {
        this.maxStudentCount = maxStudentCount;
        this.price = price;
    }

    @Override
    public boolean applyForCourse(SessionState sessionState, int price) {
        if (studentCount > maxStudentCount || sessionState != SessionState.RECRUITING || this.price != price) {
            return false;
        }
        this.studentCount++;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaidSessionStrategy)) return false;
        PaidSessionStrategy that = (PaidSessionStrategy) o;
        return studentCount == that.studentCount && maxStudentCount == that.maxStudentCount && price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentCount, maxStudentCount, price);
    }
}
