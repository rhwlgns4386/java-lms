package nextstep.courses.domain.coverimage;

public class SessionCoverImageSize {
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;

    private static final double WIDTH_RATIO = 3.0;
    private static final double HEIGHT_RATIO = 2.0;

    private Long id;
    private SessionCoverImage sessionCoverImage;

    private final int width;
    private final int height;

    public SessionCoverImageSize(int width, int height) {
        validate(width, height);
        this.width = width;
        this.height = height;
    }

    private void validate(int width, int height) {
        validateSize(width, height);
        validateRatio(width, height);
    }

    private void validateSize(int width, int height) {
        if (width < MIN_WIDTH) {
            throw new IllegalArgumentException("너비가 " + MIN_WIDTH + "미만 입니다.");
        }

        if (height < MIN_HEIGHT) {
            throw new IllegalArgumentException("높이가 " + MIN_HEIGHT + "미만 입니다.");
        }
    }
    private void validateRatio(int width, int height) {
        if (!isRatio(width, height)) {
            throw new IllegalArgumentException("비율이 " + WIDTH_RATIO + " : " + HEIGHT_RATIO + "이 아닙니다");
        }
    }

    private boolean isRatio(int width, int height) {
        double targetAspectRatio = HEIGHT_RATIO / WIDTH_RATIO;

        double currentAspectRation = (double) height / width;

        System.out.println(Math.abs(targetAspectRatio - currentAspectRation));
        return Math.abs(targetAspectRatio - currentAspectRation) < 0.01;
    }

    public void mapping(SessionCoverImage sessionCoverImage){
        this.sessionCoverImage = sessionCoverImage;
    }
}
