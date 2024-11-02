package nextstep.fixture;

import nextstep.courses.domain.*;

import java.util.ArrayList;
import java.util.List;

public class PaidSessionBuilder {
    private Long id;
    private RecruitingStatus recruitingStatus;
    private ProgressStatus progressStatus;
    private List<SessionImage> images;

    public PaidSessionBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PaidSessionBuilder withRecruitingStatus(RecruitingStatus recruitingStatus) {
        this.recruitingStatus = recruitingStatus;
        return this;
    }

    public PaidSessionBuilder withProgressingStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
        return this;
    }

    public PaidSessionBuilder withImages(List<SessionImage> images) {
        this.images = images;
        return this;
    }

    public PaidSession build() {
        return new PaidSession(id, SessionDateCreator.standard(), images, recruitingStatus, progressStatus, new ArrayList<>(), 80, 25000);
    }
}
