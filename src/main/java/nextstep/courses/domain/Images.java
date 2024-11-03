package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Images {
    private final Long sessionId;
    private final List<Image> images;

    public Images(Long sessionId) {
        this(sessionId, new ArrayList<>());
    }

    public Images(Long sessionId, List<Image> images) {
        this.sessionId = sessionId;
        this.images = new ArrayList<>(images);
    }

    public void add(Image sessionImage) {
        this.images.add(sessionImage);
    }

    public int size() {
        return this.images.size();
    }

    public Long getSessionId() {
        return sessionId;
    }

    public List<Image> getImages() {
        return images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Images))
            return false;
        Images images1 = (Images)o;
        return Objects.equals(getSessionId(), images1.getSessionId()) && Objects.equals(getImages(), images1.getImages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionId(), getImages());
    }
}
