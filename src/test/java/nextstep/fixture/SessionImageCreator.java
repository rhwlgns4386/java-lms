package nextstep.fixture;

import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionImageType;

public class SessionImageCreator {

    public static SessionImage standard() {
        return new SessionImage(1L, 512, SessionImageType.getExtension("png"), SessionImageSizeCreator.standard());
    }

    public static SessionImage volume(int volume) {
        return new SessionImage(1L, volume, SessionImageType.getExtension("png"), SessionImageSizeCreator.standard());
    }
}
