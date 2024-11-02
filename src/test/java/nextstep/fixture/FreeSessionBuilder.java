package nextstep.fixture;

import nextstep.courses.domain.*;

import java.util.ArrayList;
import java.util.List;

public class FreeSessionBuilder {
    private Long id;
    private RecruitingStatus recruitingStatus;
    private ProgressStatus progressStatus;
    private List<SessionImage> images;

    public FreeSessionBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public FreeSessionBuilder withRecruitingStatus(RecruitingStatus recruitingStatus) {
        this.recruitingStatus = recruitingStatus;
        return this;
    }

    public FreeSessionBuilder withProgressingStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
        return this;
    }

    public FreeSessionBuilder withImages(List<SessionImage> images) {
        this.images = images;
        return this;
    }

    public FreeSession build() {
        return new FreeSession(id, SessionDateCreator.standard(), images, recruitingStatus, progressStatus, new ArrayList<>());
    }
}
