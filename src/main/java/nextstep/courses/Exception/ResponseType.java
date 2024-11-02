package nextstep.courses.Exception;

public enum ResponseType {
    NOT_ALLOWED_DATE_MESSAGE("허용되지 않은 시작날짜입니다."),
    OVER_MAX_IMAGE_CAPACITY("이미지 사이즈는 1MB 이하여야 합니다."),
    IMAGE_SIZE_ERROR("이미지 사이즈 오류입니다."),
    IMAGE_PERCENT_ERROR("이미지 비율 오류"),
    NOT_ALLOWED_PREMIUM_AMOUNT("유료강의 금액은 0원이 될 수 없습니다."),
    NOT_ALLOWED_FREE_AMOUNT("무료 강의 금액이 0원이 아닙니다."),
    NOT_MACHING_SESSION_AMOUNT("강의 금액과 맞지 않습니다."),
    MAX_STUDENTS_OVER("수강인원 초과"),
    INVALID_SESSION_STATE("강의신청 기간이 아닙니다."),
    INVALID_IMAGE_TYPE("이미지 타입이 올바르지 않습니다.");

    public String message;

    ResponseType(String message) {
        this.message = message;
    }
}
