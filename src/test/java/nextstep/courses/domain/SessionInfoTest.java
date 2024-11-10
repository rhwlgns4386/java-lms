package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SessionInfoTest {
    public final static SessionInfo SESSION_INFO_RECRUIT_READY = new SessionInfo(new SessionMetaData("모집중_준비중", "작성자1"),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
            StateCode.RECRUITING, ProgressCode.READY, new Instructor(new InstructorId(1)));

    public final static SessionInfo SESSION_INFO_RECRUIT_PROGRESS = new SessionInfo(new SessionMetaData("모집중_진행중","작성자2"),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
            StateCode.RECRUITING, ProgressCode.PROGRESS, new Instructor(new InstructorId(1)));

    public final static SessionInfo SESSION_INFO_RECRUIT_END = new SessionInfo(new SessionMetaData("모집중_종료", "작성자3"),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
            StateCode.RECRUITING, ProgressCode.END, new Instructor(new InstructorId(2)));

    public final static SessionInfo SESSION_INFO_NO_RECRUIT_READY = new SessionInfo(new SessionMetaData("비모집_진행중", "createorId"),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
            StateCode.NO_RECRUITING, ProgressCode.READY,  new Instructor(new InstructorId(2)));

    public final static SessionInfo SESSION_INFO_NO_RECRUIT_PROGRESS = new SessionInfo(new SessionMetaData("비모집_진행중", "createorId"),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
            StateCode.NO_RECRUITING, ProgressCode.PROGRESS,  new Instructor(new InstructorId(3)));

    public final static SessionInfo SESSION_INFO_NO_RECRUIT_END = new SessionInfo(new SessionMetaData("비모집_종료", "createorId"),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
            StateCode.NO_RECRUITING, ProgressCode.END,  new Instructor(new InstructorId(3)));
}
