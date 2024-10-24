package nextstep.courses.domain.session.cover.validator;

import java.io.File;
import nextstep.courses.domain.session.cover.ImageValidator;

public class FileSizeValidator implements ImageValidator {

    private static final int MAX_FILE_SIZE = 1024 * 1024;

    @Override
    public void validate(File file) {
        if (file.length() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("invalid");
        }
    }
}
