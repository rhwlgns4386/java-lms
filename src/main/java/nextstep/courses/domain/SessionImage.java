package nextstep.courses.domain;

import java.util.Objects;

public class SessionImage {
    private final long id;
    private final int volume;
    private final SessionImageType type;
    private final SessionImageSize sessionImageSize;

    public SessionImage(long id, int volume, String type, int width, int height) {
        this(id, volume, SessionImageType.valueOf(type), new SessionImageSize(width, height));
    }

    public SessionImage(int volume, SessionImageType type, SessionImageSize sessionImageSize) {
        this(0L, volume, type, sessionImageSize);
    }

    public SessionImage(long id, int volume, SessionImageType type, SessionImageSize sessionImageSize) {
        if (volume > 1024) {
            throw new IllegalArgumentException("이미지의 용량은 1MB를 초과할 수 없습니다.");
        }
        this.id = id;
        this.volume = volume;
        this.type = type;
        this.sessionImageSize = sessionImageSize;
    }

    public long getId() {
        return id;
    }

    public int getVolume() {
        return volume;
    }

    public SessionImageType getType() {
        return type;
    }

    public SessionImageSize getSessionImageSize() {
        return sessionImageSize;
    }

    @Override
    public String toString() {
        return "SessionImage{" +
                "id=" + id +
                ", volume=" + volume +
                ", type=" + type +
                ", sessionImageSize=" + sessionImageSize +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SessionImage that = (SessionImage) object;
        return getId() == that.getId() && getVolume() == that.getVolume() && getType() == that.getType() && Objects.equals(getSessionImageSize(), that.getSessionImageSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVolume(), getType(), getSessionImageSize());
    }
}
