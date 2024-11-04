package nextstep.courses.domain.enrollment;

import nextstep.courses.type.SelectionType;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Enrollment {

    public static final int INFINITE_ENROLLMENT = -1;

    private int enrollment;
    private final int maxEnrollment;
    private final SelectionType selectionType;
    private final Map<Long, Student> students = new HashMap<>();

    public Enrollment(int maxEnrollment) {
        this(0, maxEnrollment);
    }

    public Enrollment(int maxEnrollment, SelectionType selectionType) {
        this(0, maxEnrollment, selectionType);
    }

    public Enrollment(int maxEnrollment, List<Student> students) {
        this(0, maxEnrollment, students);
    }

    public Enrollment(int maxEnrollment, List<Student> students, SelectionType selectionType) {
        this(0, maxEnrollment, students, selectionType);
    }

    public Enrollment(int enrollment, int maxEnrollment) {
        this(enrollment, maxEnrollment, new ArrayList<>());
    }

    public Enrollment(int enrollment, int maxEnrollment, SelectionType selectionType) {
        this(enrollment, maxEnrollment, new ArrayList<>(), selectionType);
    }

    public Enrollment(int enrollment, int maxEnrollment, List<Student> students) {
        this(enrollment, maxEnrollment, students, SelectionType.NON_SELECTION);
    }

    public Enrollment(int enrollment, int maxEnrollment, List<Student> students, SelectionType selectionType) {
        this.maxEnrollment = maxEnrollment;
        this.enrollment = enrollment;
        this.selectionType = selectionType;
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
        Student student = getStudent(user.getId());
        validateSelection(student);

        student.approve();
        register();
        return student;
    }

    private Student getStudent(Long id) {
        return Optional.ofNullable(students.get(id))
                .orElseThrow(() -> new IllegalArgumentException("수강 신청한 사람이 아닙니다"));
    }

    private void validateSelection(Student student) {
        if (selectionType == SelectionType.SELECTION && !student.isSelected()) {
            throw new IllegalStateException("선발된 인원만 수강 신청 완료할 수 있습니다");
        }
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

    public Student select(NsUser user) {
        Student student = getStudent(user.getId());
        student.select();
        return student;
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

    public SelectionType getSelectionType() {
        return selectionType;
    }
}
