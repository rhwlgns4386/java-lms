package nextstep.courses.Exception;

public class CustomException extends RuntimeException {

    public static final CustomException NOT_ALLOWED_DATE = new CustomException(ResponseType.NOT_ALLOWED_DATE_MESSAGE);
    public static final CustomException OVER_MAX_IMAGE_CAPACITY = new CustomException(ResponseType.OVER_MAX_IMAGE_CAPACITY);
    public static final CustomException IMAGE_SIZE_ERROR = new CustomException(ResponseType.IMAGE_SIZE_ERROR);
    public static final CustomException IMAGE_PERCENT_ERROR = new CustomException(ResponseType.IMAGE_PERCENT_ERROR);
    public static final CustomException NOT_ALLOWED_PREMIUM_AMOUNT = new CustomException(ResponseType.NOT_ALLOWED_PREMIUM_AMOUNT);
    public static final CustomException NOT_ALLOWED_FREE_AMOUNT = new CustomException(ResponseType.NOT_ALLOWED_FREE_AMOUNT);
    public static final CustomException NOT_MATCHING_SESSION_AMOUNT = new CustomException(ResponseType.NOT_MACHING_SESSION_AMOUNT);
    public static final CustomException MAX_STUDENTS_OVER = new CustomException(ResponseType.MAX_STUDENTS_OVER);
    public static final CustomException INVALID_SESSION_STATE = new CustomException(ResponseType.INVALID_SESSION_STATE);
    public static final CustomException INVALID_IMAGE_TYPE = new CustomException(ResponseType.INVALID_IMAGE_TYPE);

    public CustomException(ResponseType responseType) {
        super(responseType.message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }


}
