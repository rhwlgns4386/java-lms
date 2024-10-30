package nextstep.courses.domain;


import nextstep.courses.domain.Image.CoverImage;
import nextstep.payments.domain.Payment;

public class Session {
    private static final int MAX_STUDENT_COUNT = 999;
    private Long id;
    private final String name;
    private final String description;
    private final CoverImage image;
    private final SessionState state;
    private final SessionDetail sessionDetail;
    private final SessionDate sessionDate;


    public Session(Long id, String name, String description, CoverImage image, boolean isFree, int maxStudentCount, Long sessionFee, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.state = SessionState.READY;
        this.sessionDate = new SessionDate(startDate, endDate);
        this.sessionDetail = new SessionDetail(isFree, isFree ? MAX_STUDENT_COUNT : maxStudentCount, sessionFee);
    }
    public Session(Long id, String name, String description, CoverImage image, boolean isFree, Long sessionFee, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.state = SessionState.READY;
        this.sessionDate = new SessionDate(startDate, endDate);
        this.sessionDetail = new SessionDetail(isFree, MAX_STUDENT_COUNT, sessionFee);
    }

    public Session(Long id, String name, String description, CoverImage image, String startDate, String endDate) {
        this(id, name, description, image, true, MAX_STUDENT_COUNT, 0L, startDate, endDate);
    }

    public Session(Long id, String name, String description, CoverImage image, int maxStudentCount, Long sessionFee, String startDate, String endDate) {
        this(id, name, description, image, false, maxStudentCount, sessionFee, startDate, endDate);
    }

    public Session(Long id, String name, String description, boolean isFree, int maxStudentCount, Long sessionFee, String startDate, String endDate) {
        this(id, name, description, null, isFree, maxStudentCount, sessionFee, startDate, endDate);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Payment register(Student student, Long amount) {
        if (!state.isOpen()) {
            throw new IllegalStateException("아직 접수 기간이 아닙니다.");
        }
        sessionDetail.registerNewStudent(student, amount);
        return new Payment("0", this.id, student.getId(), amount);
    }
}
