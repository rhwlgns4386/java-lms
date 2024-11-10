package nextstep.courses.domain;

import java.util.Objects;

public class InstructorId {

    private final long id;

    public InstructorId(long id) {
        this.id = id;
    }


    public long getInstructorId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructorId that = (InstructorId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
