package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImages;
import nextstep.courses.domain.enrollment.Student;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import nextstep.courses.type.SessionType;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static nextstep.courses.domain.session.Session.NOT_ASSIGNED;

public class SessionBuilder {

    private Long id;
    private int enrollment;
    private int maxEnrollment = NOT_ASSIGNED;
    private long sessionFee = NOT_ASSIGNED;
    private SessionState sessionState = SessionState.PREPARING;
    private RecruitState recruitState = RecruitState.NOT_RECRUIT;
    private SessionType sessionType;
    private CoverImage coverImage;
    private final Set<CoverImage> coverImages = new HashSet<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Student> students = new ArrayList<>();

    private SessionBuilder() {
    }

    public static SessionBuilder builder() {
        return new SessionBuilder();
    }

    public SessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public SessionBuilder enrollment(int enrollment) {
        this.enrollment = enrollment;
        return this;
    }

    public SessionBuilder maxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
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

    public SessionBuilder recruitState(RecruitState recruitState) {
        this.recruitState = recruitState;
        return this;
    }

    public SessionBuilder sessionType(SessionType sessionType) {
        this.sessionType = sessionType;
        return this;
    }

    public SessionBuilder coverImages(List<CoverImage> coverImages) {
        this.coverImages.addAll(coverImages);
        return this;
    }

    public SessionBuilder coverImages(CoverImage... coverImages) {
        this.coverImages.addAll(Arrays.asList(coverImages));
        return this;
    }

    public SessionBuilder coverFilePaths(List<String> coverFilePaths) {
        this.coverImages.addAll(coverFilePaths.stream().map(CoverImage::of).collect(Collectors.toList()));
        return this;
    }

    public SessionBuilder coverFilePaths(String... coverFilePaths) {
        this.coverImages.addAll(Arrays.stream(coverFilePaths).map(CoverImage::of).collect(Collectors.toList()));
        return this;
    }

    public SessionBuilder coverImage(String coverImagePath) {
        this.coverImage = CoverImage.of(coverImagePath);
        setCoverImages(coverImage);
        return this;
    }

    public SessionBuilder coverImage(File coverImageFile) {
        this.coverImage = CoverImage.of(coverImageFile);
        setCoverImages(coverImage);
        return this;
    }

    private void setCoverImages(CoverImage coverImage) {
        coverImages.add(coverImage);
    }

    public SessionBuilder startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public SessionBuilder endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public SessionBuilder students(List<Student> students) {
        this.students = students;
        return this;
    }

    public Session build() {
        if (coverImages.isEmpty()) {
            throw new IllegalArgumentException("강의 커버가 입력되지 않았습니다");
        }
        validateEmpty(startDate, "강의 시작일이 입력되지 않았습니다");
        validateEmpty(endDate, "강의 종료일이 입력되지 않았습니다");
        validateEmpty(sessionType, "강의 타입이 입력되지 않았습니다");

        if (sessionType == SessionType.FREE) {
            return new FreeSession(getId(), getCoverImage(), toCoverImages(), sessionState, recruitState,
                    enrollment, startDate, endDate, students);
        }
        validateValueAssignment(maxEnrollment, "유료 강의는 수강 인원이 필수 값입니다");
        validateValueAssignment(sessionFee, "유료 강의는 수강료가 필수 값입니다");
        return new PaidSession(getId(), getCoverImage(), toCoverImages(), sessionState, recruitState,
                maxEnrollment, enrollment, sessionFee, startDate, endDate, students);
    }

    private Long getId() {
        return (id == null) ? NOT_ASSIGNED : id;
    }

    private void validateEmpty(Object toValidate, String errorMessage) {
        Optional.ofNullable(toValidate).orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

    private CoverImage getCoverImage() {
        return coverImage == null ? coverImages.iterator().next() : coverImage;
    }

    private CoverImages toCoverImages() {
        return new CoverImages(new ArrayList<>(coverImages));
    }

    private void validateValueAssignment(long toValidate, String errorMessage) {
        if (toValidate == NOT_ASSIGNED) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
