package nextstep.fixture;

import nextstep.courses.domain.SessionImageSize;

public class SessionImageSizeCreator {

    public static SessionImageSize standard() {
        return new SessionImageSize(400, 300);
    }

    public static SessionImageSize size(int width, int height) {
        return new SessionImageSize(width, height);
    }
}
