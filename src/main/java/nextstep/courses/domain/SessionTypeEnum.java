package nextstep.courses.domain;

public enum SessionTypeEnum {
    PAID("01", "유료"),
    FREE("02", "무료");

    private String typeCode ;
    private String description;

    SessionTypeEnum(String typeCode, String description) {
        this.typeCode = typeCode;
        this.description = description;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getDescription() {
        return description;
    }
}
