package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionTestFixture {

    public static Session createFreeSession(Long id, SessionStatus sessionStatus, RegisterStatus registerStatus) {
        SessionDate sessionDate = createSessionDate();
        Images sessionImages = createSessionImages();

        return new Session(id, 1L, 1L, 3000L, sessionStatus, registerStatus, 0, sessionDate, sessionImages, Type.FREE);
    }

    public static Session createPaidSession(Long id, long price, int maxSize, SessionStatus sessionStatus, RegisterStatus registerStatus) {
        SessionDate sessionDate = createSessionDate();
        Images sessionImages = createSessionImages();

        return new Session(id, 1L, 1L, price, sessionStatus, registerStatus, maxSize, sessionDate, sessionImages, Type.PAID);
    }

    public static Image createSessionImage() {
        ImageSize imageSize = new ImageSize(300, 200);
        ImageMetaData imageMetaData = new ImageMetaData(1, Extension.JPG);
        return new Image(imageSize, imageMetaData);
    }

    public static Images createSessionImages() {
        List<Image> imageList = List.of(
            createSessionImage(),
            createSessionImage(),
            createSessionImage(),
            createSessionImage()
        );
        return new Images(1L, imageList);
    }

    public static Images createSessionImages(Long... ids) {
        List<Image> sessionImagesList = new ArrayList<>();
        for (Long id : ids) {
            sessionImagesList.add(createSessionImage(id));
        }
        return new Images(1L, sessionImagesList);
    }

    private static Image createSessionImage(Long id) {
        ImageSize imageSize = new ImageSize(300, 200);
        ImageMetaData imageMetaData = new ImageMetaData(1, Extension.JPG);
        return new Image(id, imageSize, imageMetaData);
    }

    public static SessionDate createSessionDate() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 12);

        return new SessionDate(start, end);
    }
}
