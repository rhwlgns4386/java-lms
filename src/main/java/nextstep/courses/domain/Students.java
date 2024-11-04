package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Students {
    private final String OVER_MAX_CAPACITY = "최대 수강 인원을 초과했습니다.";
    private final List<NsUser> students;
    private final Optional<Integer> maxCapacity;

    public Students() {
        this(new ArrayList<>(), Optional.empty());
    }

    public Students(int maxCapacity) {
        this(new ArrayList<>(), Optional.of(maxCapacity));
    }

    public Students(List<NsUser> students, Optional<Integer> maxCapacity) {
        this.students = students;
        this.maxCapacity = maxCapacity;
    }

    public boolean canAddStudent() {
        return maxCapacity.map(capacity -> students.size() < capacity).orElse(true);
    }

    public void addStudent(NsUser student) {
        if (!canAddStudent()) {
            throw new IllegalArgumentException(OVER_MAX_CAPACITY);
        }
        students.add(student);
    }

    public int getMaxCapacity() {
        return maxCapacity.orElse(0);
    }
}
