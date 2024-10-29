package nextstep.courses.domain;

public enum SessionStatus {
    PREPARING,
    RECRUITING,
    END
    ;

    public boolean isOpen(){
        return this == RECRUITING;
    }
}
