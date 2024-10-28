package nextstep.courses.domain;

public enum CourseType {
    FREE("free"),
    PAID("paid");

    private String code;

    CourseType(String code) {
        this.code = code;
    }
}
