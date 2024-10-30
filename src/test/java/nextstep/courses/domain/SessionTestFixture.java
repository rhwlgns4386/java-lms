package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionTestFixture {

    public static Session createFreeSession(Long id, SessionStatus status) {
        SessionDate sessionDate = createSessionDate();
        SessionImage sessionImage = createSessionImage();

        return new Session(id, 1L, 3000L, status, 0, sessionDate, sessionImage, Type.FREE);
    }

    public static Session createPaidSession(Long id, long price, int maxSize, SessionStatus status) {
        SessionDate sessionDate = createSessionDate();
        SessionImage sessionImage = createSessionImage();

        return new Session(id, 1L, price, status, maxSize, sessionDate, sessionImage, Type.PAID);
    }

    public static SessionImage createSessionImage() {
        ImageSize imageSize = new ImageSize(300, 200);
        ImageMetaData imageMetaData = new ImageMetaData(1, Extension.JPG);
        return new SessionImage(imageSize, imageMetaData);
    }

    public static SessionDate createSessionDate() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 12);

        return new SessionDate(start, end);
    }
}
