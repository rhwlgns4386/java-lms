package nextstep.courses.domain.session.cover.validator;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nextstep.courses.domain.session.cover.ImageValidator;

public class FileExtensionValidator implements ImageValidator {

        private static final Set<String> ALLOWED_FILE_EXTENSIONS
            = new HashSet<>(Arrays.asList("gif", "jpeg", "jpg", "png"));

        @Override
        public void validate(File file) {
            if (!ALLOWED_FILE_EXTENSIONS.contains(parseExtension(file.getName()))) {
                throw new IllegalArgumentException("invalid");
            }
        }

        private String parseExtension(String filename) {
            int index = filename.lastIndexOf(".");
            return (index > -1) ? filename.substring(index + 1).toLowerCase() : "";
        }
    }