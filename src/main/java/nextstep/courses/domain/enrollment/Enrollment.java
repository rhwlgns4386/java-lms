package nextstep.courses.domain.enrollment;

import nextstep.users.domain.NsUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Enrollment {

    public static final int INFINITE_ENROLLMENT = -1;

    private int enrollment;
    private final int maxEnrollment;
    private final Map<Long, Student> students = new HashMap<>();

    public Enrollment(int maxEnrollment) {
        this(0, maxEnrollment);
    }

    public Enrollment(int maxEnrollment, List<Student> students) {
        this(0, maxEnrollment, students);
    }

    public Enrollment(int enrollment, int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
        this.enrollment = enrollment;
    }

    public Enrollment(int enrollment, int maxEnrollment, List<Student> students) {
        this.maxEnrollment = maxEnrollment;
        this.enrollment = enrollment;
        students.forEach(student -> this.students.put(student.getStudent().getId(), student));
    }

    public void apply(Student user) {
        Long studentId = user.getStudent().getId();
        duplicateCheck(studentId);
        validateAvailability();
        students.put(studentId, user);
    }

    private void duplicateCheck(Long studentId) {
        if (students.containsKey(studentId)) {
            throw new IllegalArgumentException("이미 수강 신청된 신청자입니다");
        }
    }

    public Student register(NsUser user) {
        Student student = Optional.ofNullable(students.get(user.getId()))
                .orElseThrow(() -> new IllegalArgumentException("수강 신청한 사람이 아닙니다"));
        student.approve();
        register();
        return student;
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

    public Student reject(NsUser student) {
        Student foundStudent = Optional.ofNullable(students.get(student.getId()))
                .orElseThrow(() -> new IllegalArgumentException("수강 신청한 사람이 아닙니다"));
        foundStudent.reject();
        return foundStudent;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }
}
