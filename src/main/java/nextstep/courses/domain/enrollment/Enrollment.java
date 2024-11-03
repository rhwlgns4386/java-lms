package nextstep.courses.domain.enrollment;

import nextstep.users.domain.NsUser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Enrollment {

    public static final int INFINITE_ENROLLMENT = -1;

    private int enrollment;
    private final int maxEnrollment;
    private final Map<Long, Student> students = new HashMap<>();

    public Enrollment(int maxEnrollment) {
        this(0, maxEnrollment);
    }

    public Enrollment(int enrollment, int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
        this.enrollment = enrollment;
    }

    public void apply(NsUser student) {
        duplicateCheck(student);
        validateAvailability();
        students.put(student.getId(), new Student(student));
    }

    private void duplicateCheck(NsUser student) {
        if (students.containsKey(student.getId())) {
            throw new IllegalArgumentException("이미 수강 신청된 신청자입니다");
        }
    }

    public void register(NsUser student) {
        Optional.ofNullable(students.get(student.getId()))
                .orElseThrow(() -> new IllegalArgumentException("수강 신청한 사람이 아닙니다"))
                .approve();
        register();
    }

    public void register() {
        validateAvailability();
        enrollment++;
    }

    public void validateAvailability() {
        if (isNotAvailable()) {
            throw new IllegalStateException("강의 수강 가능 인원이 초과되었습니다");
        }
    }

    public boolean isNotAvailable() {
        return maxEnrollment != INFINITE_ENROLLMENT && getEnrollment() >= maxEnrollment;
    }

    public void reject(NsUser student) {
        Optional.ofNullable(students.get(student.getId()))
                .orElseThrow(() -> new IllegalArgumentException("수강 신청한 사람이 아닙니다"))
                .reject();
    }

    public int getEnrollment() {
        return enrollment;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }
}
