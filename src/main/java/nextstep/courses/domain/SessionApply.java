package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SessionApply {
    private final int maxPersonnel;
    private final SessionRecruitment recruitment;
    private final SessionProgressStatus progressStatus;
    private List<Student> students;

    public SessionApply(int maxPersonnel, SessionRecruitment recruitment, SessionProgressStatus progressStatus, List<Student> students) {
        this.maxPersonnel = maxPersonnel;
        this.recruitment = recruitment;
        this.progressStatus = progressStatus;
        this.students = new ArrayList<>(students);
    }

    public void apply(Student student) {
        validStatus();
        validContains(student);
        validMaxPersonnel();
        students.add(student);
    }

    private void validContains(Student student) {
        if (students.contains(student)) {
            throw new IllegalArgumentException("Student is already in the list.");
        }
    }

    private void validMaxPersonnel() {
        if (students.size() >= maxPersonnel) {
            throw new IllegalArgumentException("Max personnel exceeded.");
        }
    }

    private void validStatus() {
        if (progressStatus.isClosed() || !recruitment.isRecruiting()) {
            throw new IllegalArgumentException("Session is not recruiting.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionApply that = (SessionApply) o;
        return maxPersonnel == that.maxPersonnel && recruitment == that.recruitment && Objects.equals(students, that.students);
    }
}
