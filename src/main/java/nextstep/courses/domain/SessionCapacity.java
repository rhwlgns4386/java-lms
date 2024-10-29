package nextstep.courses.domain;

public class SessionCapacity {
    private int capacity;
    private int currentCount;

    public SessionCapacity(int capacity) {
        this(capacity, 0);
    }

    public SessionCapacity(int capacity, int currentCount) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.currentCount = currentCount;
    }


    public void increase() {
        this.currentCount++;
        if(this.currentCount > this.capacity){
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
        return capacity == that.capacity && currentCount == that.currentCount;
    }

    @Override
    public int hashCode() {
        int result = capacity;
        result = 31 * result + currentCount;
        return result;
    }
}
