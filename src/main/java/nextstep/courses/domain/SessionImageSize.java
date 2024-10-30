package nextstep.courses.domain;

import java.util.Objects;

public class SessionImageSize {
    private final int witdh;
    private final int height;

    public SessionImageSize(int width, int height) {
        if (width < 300 || height < 200) {
            throw new IllegalArgumentException("이미지의 규격이 맞지 않습니다.");
        }
        this.witdh = width;
        this.height = height;
    }

    public int getWitdh() {
        return witdh;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SessionImageSize that = (SessionImageSize) object;
        return getWitdh() == that.getWitdh() && getHeight() == that.getHeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWitdh(), getHeight());
    }
}
