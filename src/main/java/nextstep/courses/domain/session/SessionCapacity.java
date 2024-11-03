package nextstep.courses.domain.session;

public class SessionCapacity {
    private int capacity;

    public SessionCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void checkCapacity(int countOfStudents) {
        if (countOfStudents > this.capacity) {
            throw new IllegalArgumentException("Capacity is full");
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionCapacity)) {
            return false;
        }

        SessionCapacity that = (SessionCapacity) o;
        return getCapacity() == that.getCapacity();
    }

    @Override
    public int hashCode() {
        return getCapacity();
    }
}
