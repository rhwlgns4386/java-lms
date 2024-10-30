package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CoverImageTest {

    @TempDir
    private File anotherTempDir;

    @Test
    @DisplayName("실패 - 파일 크기가 1MB 이상일 때 예외를 반환한다.")
    void throwExceptionWhenInvalidFileSize() throws Exception {
        File largeFile = new File(anotherTempDir, "largeFile.png");
        byte[] largeData = new byte[1024 * 1024 + 1];
        Files.write(largeFile.toPath(), largeData);

        assertThatThrownBy(() -> CoverImage.of(largeFile))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미지 크기는 1MB 이하여야 합니다.");
    }

    @Test
    @DisplayName("실패 - 이미지 비율이 3:2가 아닐 때 예외를 반환한다.")
    void throwExceptionWhenInvalidImageRatio() throws Exception {
        BufferedImage invalidRatioImage = new BufferedImage(300, 250, BufferedImage.TYPE_INT_RGB);
        File invalidFile = new File(anotherTempDir, "invalidImage.png");
        ImageIO.write(invalidRatioImage, "png", invalidFile);

        assertThatThrownBy(() -> CoverImage.of(invalidFile))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미지 너비와 높이의 비율은 3:2여야 합니다.");
    }

    @Test
    @DisplayName("실패 - 이미지 너비가 300픽셀 미만일 때 예외를 반환한다.")
    void throwExceptionWhenInvalidWidth() throws Exception {
        BufferedImage invalidRatioImage = new BufferedImage(299, 200, BufferedImage.TYPE_INT_RGB);
        File invalidFile = new File(anotherTempDir, "invalidImage.png");
        ImageIO.write(invalidRatioImage, "png", invalidFile);

        assertThatThrownBy(() -> CoverImage.of(invalidFile))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미지 크기가 맞지 않습니다.(너비 300픽셀 이상, 높이 200픽셀 이상)");
    }

    @Test
    @DisplayName("실패 - 이미지 높이가 200픽셀 미만일 때 예외를 반환한다.")
    void throwExceptionWhenInvalidHeight() throws Exception {
        BufferedImage invalidRatioImage = new BufferedImage(300, 199, BufferedImage.TYPE_INT_RGB);
        File invalidFile = new File(anotherTempDir, "invalidImage.png");
        ImageIO.write(invalidRatioImage, "png", invalidFile);

        assertThatThrownBy(() -> CoverImage.of(invalidFile))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미지 크기가 맞지 않습니다.(너비 300픽셀 이상, 높이 200픽셀 이상)");
    }

    @ParameterizedTest
    @ValueSource(strings = {"gif", "jpg", "jpeg", "png", "svg"})
    @DisplayName("성공 - 올바른 확장자 이미지 파일이 주어졌을 때 객체가 생성된다.")
    void extensionTest(String extension) throws Exception {
        BufferedImage invalidRatioImage = new BufferedImage(300, 200, BufferedImage.TYPE_INT_RGB);
        File validFile = new File(anotherTempDir, "invalidImage." + extension);
        ImageIO.write(invalidRatioImage, extension, validFile);

        assertThat(CoverImage.of(validFile))
            .isNotNull();
    }

    @Test
    @DisplayName("실패 - 지원하지 않는 확장자 이미지 파일이 주어졌을 때 예외를 반환한다.")
    void throwExceptionWhenInvalidExtension() throws Exception {
        BufferedImage invalidRatioImage = new BufferedImage(300, 200, BufferedImage.TYPE_INT_RGB);
        File invalidFile = new File(anotherTempDir, "validFile.bmp");
        ImageIO.write(invalidRatioImage, "bmp", invalidFile);

        assertThatThrownBy(() -> CoverImage.of(invalidFile))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("지원하지 않는 확장자 입니다.");
    }
}