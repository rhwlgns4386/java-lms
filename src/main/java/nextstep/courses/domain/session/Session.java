package nextstep.courses.domain.session;

import nextstep.courses.domain.PaymentType;
import nextstep.courses.domain.image.SessionImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {
    private final Long id;
    private final SessionDate sessionDate;
    private final SessionImage sessionImage;
    private final PaymentType paymentType;
    private String title;
    private int price;
    private final Subscription subscription;

    public Session(Long id, SessionDate sessionDate, SessionImage sessionImage, PaymentType paymentType,
                    SessionStatus sessionStatus, int subscribeMax, String title, int price) {
        this(id, sessionDate, sessionImage, paymentType, new Subscription(sessionStatus, subscribeMax), title, price);
    }

    public Session(Long id, SessionDate sessionDate, SessionImage sessionImage, PaymentType paymentType,
                    Subscription subscription, String title, int price) {
        this.id = id;
        this.sessionDate = sessionDate;
        this.sessionImage = sessionImage;
        this.paymentType = paymentType;
        this.subscription = subscription;
        this.title = title;
        this.price = price;
    }

    public void subscribe (NsUser user, Payment payment) throws SessionException {
        if (isFree()) {
            subscription.subscribe(user);
            return;
        }
        subscription.subscribe(user, payment.isSame(price));
    }

    public boolean isFree() {
        return this.price == 0;
    }

    public Long getId() {
        return id;
    }

    public int getSubscribeCount() {
        return subscription.getSubscribeCount();
    }
}
