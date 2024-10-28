package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nextstep.users.domain.NsUser;

public class Students {
    private final List<NsUser> students;
    private final Integer maxSize;

    public Students() {
        this(new ArrayList<>(), null);
    }

    public Students(int maxSize) {
        this(new ArrayList<>(), maxSize);
    }

    public Students(List<NsUser> students) {
        this(new ArrayList<>(students), null);
    }

    public Students(List<NsUser> students, Integer maxSize) {
        this.students = students;
        this.maxSize = maxSize;
    }

    public void add(NsUser newStudent) {
        if (maxSize != null && students.size() + 1 > maxSize) {
            throw new IllegalArgumentException("Maximum student count exceeded");
        }
        this.students.add(newStudent);
    }

    public int size() {
        return this.students.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Students))
            return false;
        Students students1 = (Students)o;
        return Objects.equals(students, students1.students) && Objects.equals(maxSize, students1.maxSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(students, maxSize);
    }
}
