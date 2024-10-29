package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sessions {
    private List<Session> sessionList;

    public Sessions(Session... session) {
        this.sessionList = new ArrayList<>(Arrays.asList(session));
    }

    public Sessions(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

}
