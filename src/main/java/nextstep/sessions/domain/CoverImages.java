package nextstep.sessions.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoverImages {
    List<CoverImage> images;

    public CoverImages() {
        this(new ArrayList<>());
    }

    public CoverImages(List<CoverImage> images) {
        this.images = images;
    }

    public List<CoverImage> getImages() {
        return Collections.unmodifiableList(images);
    }
}
