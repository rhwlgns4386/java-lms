package nextstep.fixture;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.ProgressStatus;
import nextstep.courses.domain.RecruitingStatus;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionSelection;

import java.util.ArrayList;
import java.util.List;

public class PaidSessionBuilder {
    private Long id;
    private RecruitingStatus recruitingStatus;
    private ProgressStatus progressStatus;
    private List<SessionImage> images;
    private List<Long> applyStudents;
    private SessionSelection sessionSelection;

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

    public PaidSessionBuilder withApplyStudents(List<Long> applyStudents) {
        this.applyStudents = applyStudents;
        return this;
    }

    public PaidSessionBuilder withSessionSelection(SessionSelection sessionSelection){
        this.sessionSelection = sessionSelection;
        return this;
    }

    public PaidSession build() {
        return new PaidSession(id, SessionDateCreator.standard(), images, recruitingStatus, progressStatus, new ArrayList<>(), applyStudents, 80, 25000, sessionSelection);
    }
}
