package nextstep.courses.domain.Lecturer;

import java.util.Objects;

public class Lecturer {
    private Long nsUserId;

    public Lecturer(Long nsUserId) {
        this.nsUserId = nsUserId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lecturer)) {
            return false;
        }

        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(getNsUserId(), lecturer.getNsUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNsUserId());
    }
}
