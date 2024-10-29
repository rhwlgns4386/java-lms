package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Student extends NsUser {
    private List<Session> sessions = new ArrayList<>();
    private int paymentAmount;

    public Student(Long id, String userId, String password, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, userId, password, name, email, createdAt, updatedAt);
    }

    public void registerSession(Session session) {
        sessions.add(session);
        paymentAmount += session.getSessionFee();
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public int getSessionCount() {
        return sessions.size();
    }
}
