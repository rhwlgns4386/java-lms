package nextstep.courses.domain;

import java.util.Arrays;

public enum ProgressCode {
    READY(10),
    PROGRESS(20),
    END(30);

    private int progressCode;

    ProgressCode(int progressCode) {
        this.progressCode = progressCode;
    }

    public static ProgressCode fromCode(int progressCode) {
        return Arrays.stream(ProgressCode.values()).filter(value -> value.progressCode == progressCode)
                .findFirst().orElseThrow(() -> {
                    throw new IllegalArgumentException("진행코드가 유효하지 않습니다.");
                });
    }

    public int getProgressCode() {
        return progressCode;
    }

    public void validateOrderSessionProgressCode() {
        if (ProgressCode.END == this) {
            throw new IllegalArgumentException("종료된 강의입니다.");
        }
    }
}
