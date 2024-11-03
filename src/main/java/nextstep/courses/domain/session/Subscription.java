package nextstep.courses.domain.session;

import java.util.ArrayList;
import java.util.List;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Subscription {
    private final SessionStatus sessionStatus;
    private final int subscribeMax;
    private final List<NsUser> subscribers;

    public Subscription(SessionStatus sessionStatus, int subscribeMax) {
        this(sessionStatus, subscribeMax, new ArrayList<>());
    }

    private Subscription(SessionStatus sessionStatus, int subscribeMax, List<NsUser> subscribers) {
        validateMaxSubscriber(subscribeMax, subscribers);
        this.sessionStatus = sessionStatus;
        this.subscribeMax = subscribeMax;
        this.subscribers = subscribers;
    }

    private void validateMaxSubscriber(int subscribeMax, List<NsUser> subscribers) {
        if (subscribeMax <= subscribers.size()) {
            throw new IllegalArgumentException("이미 정원이 다 찬 강의입니다.");
        }
    }

    public void subscribe(NsUser user) throws SessionException{
        if (!sessionStatus.isRecruiting()) {
            throw new SessionException("강의는 모집중이 아닙니다.");
        }
        if (subscribers.contains(user)) {
            throw new SessionException("이미 이 강의에 수강신청한 사용자입니다.");
        }
        subscribers.add(user);
    }

    public void subscribe(NsUser user, boolean isSameMoney) throws SessionException{
        validateMaxSubscriber(subscribeMax, subscribers);
        if (!isSameMoney) {
            throw new SessionException("결제금액과 강의 가격이 다릅니다.");
        }
        subscribe(user);
    }

    public int getSubscribeCount() {
        return subscribers.size();
    }
}
