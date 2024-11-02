package nextstep.fixture;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.ProgressStatus;
import nextstep.courses.domain.RecruitingStatus;
import nextstep.courses.domain.SessionImage;

import java.util.ArrayList;
import java.util.List;

public class FreeSessionBuilder {
    private Long id;
    private RecruitingStatus recruitingStatus;
    private ProgressStatus progressStatus;
    private List<SessionImage> images;
    private List<Long> applyStudents;

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

    public FreeSessionBuilder withApplyStudents(List<Long> applyStudents) {
        this.applyStudents = new ArrayList<>(applyStudents);
        return this;
    }


    public FreeSession build() {
        return new FreeSession(id, SessionDateCreator.standard(), images, recruitingStatus, progressStatus, new ArrayList<>(), applyStudents);
    }
}
