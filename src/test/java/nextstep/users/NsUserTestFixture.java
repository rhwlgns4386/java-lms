package nextstep.users;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class NsUserTestFixture {
    private Long id = 0L;
    private String userId = "moon";
    private String password = "1234";
    private String name = "moonyoonji";
    private String email = "moon@a.com";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = null;

    public static NsUserTestFixture builder() {
        return new NsUserTestFixture();
    }

    public NsUserTestFixture id(Long id) {
        this.id = id;
        return this;
    }

    public NsUserTestFixture userId(String userId) {
        this.userId = userId;
        return this;
    }

    public NsUserTestFixture password(String password) {
        this.password = password;
        return this;
    }

    public NsUserTestFixture name(String name) {
        this.name = name;
        return this;
    }

    public NsUserTestFixture email(String email) {
        this.email = email;
        return this;
    }

    public NsUserTestFixture createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public NsUser build() {
        return new NsUser(this.id, this.userId, this.password, this.name, this.email,
                this.createdAt, this.updatedAt);
    }
}
