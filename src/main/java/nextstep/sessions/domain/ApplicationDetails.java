package nextstep.sessions.domain;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDetails {
    private List<ApplicationDetail> applicationDetails;

    public ApplicationDetails(List<ApplicationDetail> applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

    public ApplicationDetails() {
        this(new ArrayList<ApplicationDetail>());
    }

    public void add(ApplicationDetail applicationDetail) {
        applicationDetails.add(applicationDetail);
    }

    public int size() {
        return applicationDetails.size();
    }

    public void canApply(Long sessionId, Long nsUserId) {
        ApplicationDetail applicationDetail = applicationDetails.stream()
                .filter(application -> application.getNsUserId().equals(nsUserId)
                        && application.getSessionId().equals(sessionId))
                .findAny().orElse(null);
        if (applicationDetail == null) {
            return ;
        }
        throw new RuntimeException("신청내역이 존재합니다");
    }
}
