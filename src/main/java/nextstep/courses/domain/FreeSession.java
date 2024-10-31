package nextstep.courses.domain;

public class FreeSession extends Session {


    public FreeSession(Long id,
        String title,
        SessionDate sessionDate,
        Image image
    ) {
        super(image, sessionDate, id, title, SessionStatus.PREPARING, SessionType.FREE);
    }

    @Override
    protected void register(Registration registration) {
        if (!isAvailableForRegistration()) {
            throw new IllegalStateException("Can't register session");
        }
    }

    public void register() {
        register(null);
    }


}
