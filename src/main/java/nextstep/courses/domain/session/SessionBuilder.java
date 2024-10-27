package nextstep.courses.domain.session;

import nextstep.courses.type.SessionState;
import nextstep.courses.type.SessionType;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

import static nextstep.courses.domain.session.Session.NOT_ASSIGNED;

public class SessionBuilder {

    private Long id;
    private String name;
    private int enrollment = NOT_ASSIGNED;
    private long sessionFee = NOT_ASSIGNED;
    private SessionState sessionState = SessionState.PREPARING;
    private SessionType sessionType;
    private CoverImage coverImage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private SessionBuilder() {
    }

    public static SessionBuilder builder() {
        return new SessionBuilder();
    }

    public SessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public SessionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SessionBuilder enrollment(int enrollment) {
        this.enrollment = enrollment;
        return this;
    }

    public SessionBuilder sessionFee(long sessionFee) {
        this.sessionFee = sessionFee;
        return this;
    }

    public SessionBuilder sessionState(SessionState sessionState) {
        this.sessionState = sessionState;
        return this;
    }

    public SessionBuilder sessionType(SessionType sessionType) {
        this.sessionType = sessionType;
        return this;
    }

    public SessionBuilder coverImage(String coverImagePath) {
        this.coverImage = CoverImage.of(coverImagePath);
        return this;
    }

    public SessionBuilder coverImage(File coverImageFile) {
        this.coverImage = CoverImage.of(coverImageFile);
        return this;
    }

    public SessionBuilder startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public SessionBuilder endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public Session build() {
        Optional.ofNullable(name)
                .filter(string -> !string.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("강의명이 입력되지 않았습니다"));
        validateEmpty(coverImage, "강의 커버가 입력되지 않았습니다");
        validateEmpty(startDate, "강의 시작일이 입력되지 않았습니다");
        validateEmpty(endDate, "강의 종료일이 입력되지 않았습니다");
        validateEmpty(sessionType, "강의 타입이 입력되지 않았습니다");

        if (sessionType == SessionType.FREE) {
            return new FreeSession(getId(), name, coverImage, sessionState, startDate, endDate);
        }

        validateValueAssignment(enrollment, "유료 강의는 수강 인원이 필수 값입니다");
        validateValueAssignment(sessionFee, "유료 강의는 수강료가 필수 값입니다");
        return new PaidSession(getId(), name, coverImage, sessionState, enrollment, sessionFee, startDate, endDate);
    }

    private Long getId() {
        return (id == null) ? NOT_ASSIGNED : id;
    }

    private void validateEmpty(Object toValidate, String errorMessage) {
        Optional.ofNullable(toValidate).orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

    private void validateValueAssignment(long toValidate, String errorMessage) {
        if (toValidate == NOT_ASSIGNED) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
