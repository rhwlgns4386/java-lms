package nextstep.sessions.domain;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
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

    public void add(List<ApplicationDetail> applicationDetails) {
        this.applicationDetails.addAll(applicationDetails);
    }

    public int size() {
        if (CollectionUtils.isEmpty(applicationDetails)) {
            return 0;
        }
        return applicationDetails.size();
    }

    public void canApply(Long sessionId, Long nsUserId) {
        if (applicationDetails.stream()
                .anyMatch(applicationDetail -> applicationDetail.isPresent(sessionId, nsUserId))) {

            throw new RuntimeException("신청내역이 존재합니다");
        }
    }

    public ApplicationDetail getMatch(long sessionId, Long userId) {
        return applicationDetails.stream()
                .filter(applicationDetail -> applicationDetail.isPresent(sessionId, userId))
                .findFirst()
                .orElseThrow();
    }

}
