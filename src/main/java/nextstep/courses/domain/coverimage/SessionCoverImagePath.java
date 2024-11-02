package nextstep.courses.domain.coverimage;

import nextstep.courses.CoverImageException;

import java.util.UUID;
import java.util.regex.Pattern;

public class SessionCoverImagePath {

    private static final String INVALID_CHARACTERS_REGEX = "[<>:\"/\\|?*]";
    private static final String DOT_CHAR = ".";

    private String storeFileName;
    private String originalFileName;

    public SessionCoverImagePath(String originalFileName, String storeFileName) {
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
    }

    public static SessionCoverImagePath create(String rootDir, String originalFileName) {
        validateFileName(originalFileName);
        return new SessionCoverImagePath(originalFileName, createStoreFileName(rootDir, originalFileName));
    }


    private static String createStoreFileName(String rootDir, String originalFileName) {
        SessionCoverImageType fileType = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return rootDir + uuid + DOT_CHAR + fileType.getExtension();
    }

    private static SessionCoverImageType extractExt(String originalFileName) {
        int position = originalFileName.lastIndexOf(DOT_CHAR);
        return SessionCoverImageType.search(originalFileName.substring(position + 1));
    }

    private static void validateFileName(String fileName) {
        if (!isValidFileName(fileName)) {
            throw new CoverImageException("유효하지 않는 파일 명입니다");
        }
    }

    private static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }
        if (Pattern.compile(INVALID_CHARACTERS_REGEX).matcher(fileName).find()) {
            return false;
        }
        return true;
    }

    public String getStoreFileName() {
        return storeFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }
}
