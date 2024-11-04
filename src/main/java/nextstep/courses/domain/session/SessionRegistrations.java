package nextstep.courses.domain.session;

import nextstep.users.domain.NsUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SessionRegistrations {
    private final Long sessionId;
    private final int maxStudents;
    private final Map<Long, SessionRegistration> registrations;

    public SessionRegistrations(Long sessionId, int maxStudents) {
        this(sessionId, new HashMap<>(), maxStudents);
    }

    public SessionRegistrations(Long sessionId, Map<Long, SessionRegistration> registrations, int maxStudents) {
        validateMaxStudents(maxStudents);
        this.sessionId = sessionId;
        this.registrations = new HashMap<>(registrations);
        this.maxStudents = maxStudents;
    }

    private void validateMaxStudents(int maxStudents) {
        if (maxStudents < 0) {
            throw new IllegalArgumentException("최대 수강 인원은 음수가 될 수 없습니다.");
        }
    }

    public void register(NsUser user) {
        validateRegistration(user);
        register(user.getId());
    }

    public void register(Long userId) {
        validateRegistration(userId);
        registrations.put(userId, new SessionRegistration(sessionId, userId));
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

    private void validateRegistration(Long userId) {
        if (isFull()) {
            throw new IllegalArgumentException("수강인원이 꽉 찼습니다.");
        }
        if (isAlreadyRegistered(userId)) {
            throw new IllegalArgumentException("이미 수강신청한 사용자입니다.");
        }
    }

    public void select(Long userId) {
        findRegistration(userId).select();
    }

    public void reject(Long userId) {
        findRegistration(userId).reject();
    }

    public void approve(Long userId) {
        SessionRegistration registration = findRegistration(userId);
        if (!registration.isSelected()) {
            throw new IllegalStateException("선발되지 않은 학생은 승인할 수 없습니다.");
        }
        registration.approve();
    }

    public void cancel(Long userId) {
        SessionRegistration registration = findRegistration(userId);
        if (registration.isSelected()) {
            throw new IllegalStateException("선발된 학생은 취소할 수 없습니다.");
        }
        registration.cancel();
    }

    private SessionRegistration findRegistration(Long userId) {
        SessionRegistration registration = registrations.get(userId);
        if (registration == null) {
            throw new IllegalArgumentException("수강신청 정보를 찾을 수 없습니다: " + userId);
        }
        return registration;
    }

    public boolean contains(Long userId) {
        return registrations.containsKey(userId);
    }

    public boolean isFull() {
        return getCurrentStudentCount() >= maxStudents;
    }

    public boolean isAlreadyRegistered(NsUser user) {
        return registrations.containsKey(user.getId());
    }

    public boolean isAlreadyRegistered(Long userId) {
        return registrations.containsKey(userId);
    }
    public int getCurrentStudentCount() {
        return registrations.values().size();
    }
    public List<SessionRegistration> getApprovedRegistrations() {
        return registrations.values().stream()
                .filter(SessionRegistration::isApproved)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<SessionRegistration> getPendingRegistrations() {
        return registrations.values().stream()
                .filter(SessionRegistration::isPending)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<SessionRegistration> getSelectedRegistrations() {
        return registrations.values().stream()
                .filter(SessionRegistration::isSelected)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Long> getApprovedUserIds() {
        return getApprovedRegistrations().stream()
                .map(SessionRegistration::getUserId)
                .collect(Collectors.toUnmodifiableList());
    }

}
