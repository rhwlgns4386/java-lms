package nextstep.courses.domain;

import java.time.LocalDateTime;

class SessionTest {

    public static FreeSession createFreeSession(SessionStatus status) {
        SessionDate sessionDate = createSessionDate();
        SessionImage sessionImage = createSessionImage();

        return new FreeSession(2L, 1L, sessionDate, sessionImage, status, Type.FREE);
    }

    public static PaidSession createPaidSession(long price, SessionStatus status) {
        SessionDate sessionDate = createSessionDate();
        SessionImage sessionImage = createSessionImage();

        return new PaidSession(1L, 1L, sessionDate, sessionImage, status, Type.PAID, new Students(5), price);
    }

    public static SessionImage createSessionImage() {
        ImageSize imageSize = new ImageSize(300, 200);
        ImageMetaInfo imageMetaInfo = new ImageMetaInfo(1, Extension.JPG);
        return new SessionImage(imageSize, imageMetaInfo);
    }

    public static SessionDate createSessionDate() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 12);

        return new SessionDate(start, end);
    }
}
