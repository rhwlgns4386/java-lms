package nextstep.courses.domain.session.cover.validator;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import nextstep.courses.domain.session.cover.ImageValidator;

public class ImageValidatorComposite implements ImageValidator {

    private final List<ImageValidator> validators = Arrays.asList(
        new FileExtensionValidator(),
        new FileSizeValidator(),
        new ImagePixelValidator()
    );

    @Override
    public void validate(File file) {
        validators.forEach(validator -> validator.validate(file));
    }
}