package nextstep.courses.domain.session;

import nextstep.users.domain.NsUser;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Capacity {
    private final int maxStudents;
    private Set<NsUser> currentStudents;

    public Capacity(int maxStudents) {
        this(new HashSet<>(), maxStudents);
    }

    public Capacity(Set<NsUser> registeredUsers, int maxStudents) {
        validateMaxStudents(maxStudents);
        this.currentStudents = new HashSet<>(registeredUsers);
        this.maxStudents = maxStudents;
    }

    private void validateMaxStudents(int maxStudents) {
        if (maxStudents <= 0) {
            throw new IllegalArgumentException("최대 수강 인원은 0보다 커야 합니다.");
        }
    }

    public Capacity register(NsUser user) {
        validateRegistration(user);
        Set<NsUser> newRegisteredUsers = new HashSet<>(currentStudents);
        newRegisteredUsers.add(user);
        return new Capacity(newRegisteredUsers, maxStudents);
    }

    private void validateRegistration(NsUser user) {
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보가 없습니다.");
        }
        if (isFull()) {
            throw new IllegalArgumentException("수강인원이 꽉 찼습니다.");
        }
        if (isAlreadyRegistered(user)) {
            throw new IllegalArgumentException("이미 수강신청한 사용자입니다.");
        }
    }

    public boolean isFull() {
        return currentStudents.size() >= maxStudents;
    }

    public boolean isAlreadyRegistered(NsUser user) {
        return currentStudents.contains(user);
    }

    public int getMaxStudentsSize() {
        return maxStudents;
    }

    public List<NsUser> getRegisteredStudents() {
        return currentStudents.stream()
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Long> getRegisteredStudentIds() {
        return currentStudents.stream()
                .map(NsUser::getId)
                .collect(Collectors.toList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capacity that = (Capacity) o;
        return maxStudents == that.maxStudents && Objects.equals(currentStudents, that.currentStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxStudents, currentStudents);
    }
}
