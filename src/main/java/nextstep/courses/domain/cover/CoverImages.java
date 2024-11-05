package nextstep.courses.domain.cover;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoverImages {

    private final List<CoverImage> coverImages;

    public CoverImages(List<CoverImage> coverImages) {
        if (coverImages == null || coverImages.isEmpty()) {
            throw new IllegalArgumentException("강의 커버는 필수입니다");
        }

        this.coverImages = coverImages;
    }

    public static CoverImages of(CoverImage... coverImages) {
        validateCoverArray(coverImages);
        return new CoverImages(Arrays.asList(coverImages));
    }

    public static CoverImages of(List<String> coverFilePaths) {
        if (coverFilePaths == null || coverFilePaths.isEmpty()) {
            throw new IllegalArgumentException("강의 커버는 필수입니다");
        }
        return new CoverImages(coverFilePaths.stream()
                .map(CoverImage::of)
                .collect(Collectors.toList()));
    }

    public static CoverImages of(String... coverFilePaths) {
        validateCoverArray(coverFilePaths);
        return new CoverImages(Stream.of(coverFilePaths)
                .map(CoverImage::of)
                .collect(Collectors.toList()));
    }

    private static void validateCoverArray(Object[] objects) {
        if (objects == null || objects.length == 0) {
            throw new IllegalArgumentException("강의 커버는 필수입니다");
        }
    }

    public static CoverImages of(File... coverFiles) {
        validateCoverArray(coverFiles);
        return new CoverImages(Stream.of(coverFiles)
                .map(CoverImage::of)
                .collect(Collectors.toList()));
    }

    public void add(File coverFile) {
        add(CoverImage.of(coverFile));
    }

    public void add(String coverPath) {
        add(CoverImage.of(coverPath));
    }

    public void add(CoverImage coverImage) {
        coverImages.add(coverImage);
    }

    public List<String> coverImagePaths() {
        return coverImages.stream()
                .map(CoverImage::getFilePath)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoverImages that = (CoverImages) o;
        return Objects.equals(coverImages, that.coverImages);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coverImages);
    }
}
