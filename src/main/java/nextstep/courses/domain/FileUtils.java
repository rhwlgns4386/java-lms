package nextstep.courses.domain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException("인스턴스를 생성할 수 없습니다.");
    }

    public static BufferedImage read(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지를 읽는 데 실패했습니다.");
        }
    }

    public static String getExtension(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
