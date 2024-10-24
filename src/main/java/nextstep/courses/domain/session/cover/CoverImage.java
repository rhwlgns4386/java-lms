package nextstep.courses.domain.session.cover;

import java.io.File;
import nextstep.courses.domain.session.cover.validator.ImageValidatorComposite;

public class CoverImage {

    private static final ImageValidator IMAGE_VALIDATOR = new ImageValidatorComposite();

    private final File source;

    public CoverImage(File imagePath) {
        this.source = imagePath;
    }

    public static CoverImage of(String imagePath) {
        File imageFile = new File(imagePath);
        IMAGE_VALIDATOR.validate(imageFile);

        return new CoverImage(imageFile);
    }

    public File getSource() {
        return source;
    }
}
