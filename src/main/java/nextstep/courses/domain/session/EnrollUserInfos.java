package nextstep.courses.domain.enroll;

import nextstep.courses.domain.enroll.EnrollUserInfo;

import java.util.ArrayList;
import java.util.List;

public class EnrollUserInfos {
    private static final String ENROLL_USER_ERROR = "이미 수강 신청한 회원입니다.";

    private List<EnrollUserInfo> enrollUserInfos;

    public EnrollUserInfos() {
        this(new ArrayList<>());
    }

    public EnrollUserInfos(List<EnrollUserInfo> enrollUserInfos) {
        this.enrollUserInfos = enrollUserInfos;
    }


    public int getSize() {
        return enrollUserInfos.size();
    }

    public List<EnrollUserInfo> getEnrollUserInfos() {
        return enrollUserInfos;
    }

    public void add(EnrollUserInfo enrollUserInfo) {
        if(!enrollUserInfos.contains(enrollUserInfo)) {
            throw new IllegalArgumentException(ENROLL_USER_ERROR);
        }

        enrollUserInfos.add(enrollUserInfo);
    }

}
