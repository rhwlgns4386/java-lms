package nextstep.courses.domain.cover;

import java.util.Arrays;

public enum CoverImageType {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private String code;

    CoverImageType(String code) {
        this.code = code;
    }

    public static CoverImageType getCoverImageType(String code) {
        return Arrays.stream(CoverImageType.values())
                .filter(tpye -> tpye.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("사용할 수 없는 이미지 확장자 입니다."));
    }

    public String getCode() {
        return code;
    }
}
