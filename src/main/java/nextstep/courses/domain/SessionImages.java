package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class SessionImages {

    private final List<SessionImage> sessionImages = new ArrayList<>();

    public SessionImages(List<SessionImage> sessionImages) {
        if(sessionImages.isEmpty()) {
            throw new IllegalArgumentException("이미지는 한개 이상 이여야 합니다.");
        }
        this.sessionImages.addAll(sessionImages);
    }

    public List<SessionImage> getSessionImages() {
        return sessionImages;
    }

    public int getSize() {
        return sessionImages.size();
    }

}
