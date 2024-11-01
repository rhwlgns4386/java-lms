package nextstep.users.domain;

import nextstep.global.domain.BaseEntity;
import nextstep.qna.UnAuthorizedException;

import java.time.LocalDateTime;
import java.util.Objects;

public class NsUser extends BaseEntity {
    protected String userId;

    protected String password;

    protected String name;

    protected String email;

    public NsUser() {
        super();
    }

    public NsUser(Long id, String userId, String password, String name, String email) {
        this(id, userId, password, name, email, LocalDateTime.now(), null);
    }

    public NsUser(NsUser nsUser, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(nsUser.id, nsUser.userId, nsUser.password, nsUser.name, nsUser.email, createdAt, updatedAt);
    }

    public NsUser(Long id, String userId, String password, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void update(NsUser loginUser, NsUser target) {
        if (!matchUserId(loginUser.userId)) {
            throw new UnAuthorizedException();
        }

        if (!matchPassword(target.password)) {
            throw new UnAuthorizedException();
        }

        this.name = target.name;
        this.email = target.email;
    }

    public boolean matchUser(NsUser target) {
        return matchUserId(target.userId);
    }

    private boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchId(Long id) {
        return Objects.equals(this.id, id);
    }

    public boolean matchPassword(String targetPassword) {
        return password.equals(targetPassword);
    }

    public boolean equalsNameAndEmail(NsUser target) {
        if (Objects.isNull(target)) {
            return false;
        }

        return name.equals(target.name) &&
                email.equals(target.email);
    }

    public boolean isGuestUser() {
        return false;
    }

    public boolean isSameUser(NsUser target) {
        return this.equals(target);
    }

    private static class GuestNsUser extends NsUser {
        @Override
        public boolean isGuestUser() {
            return true;
        }
    }

    @Override
    public String toString() {
        return "NsUser{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsUser nsUser = (NsUser) o;
        return Objects.equals(userId, nsUser.userId) && Objects.equals(password, nsUser.password) && Objects.equals(name, nsUser.name) && Objects.equals(email, nsUser.email)
                && Objects.equals(id, nsUser.id) && Objects.equals(createdAt, nsUser.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, password, name, email, createdAt);
    }
}
