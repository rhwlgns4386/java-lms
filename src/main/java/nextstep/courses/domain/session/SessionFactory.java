package nextstep.courses.domain.session;

public class SessionFactory {
    private SessionFactory() {
    }

    public static DefaultSession from(DefaultSession session) {
        if (SessionType.PAID.getCode().equals(session.getTypeCode())) {
            return new PaidSession(session.getId(), session.getProgress(), session.getRecruitment(), session.getPeriod(), session.getCoverImages(), session.getCourseFee(), session.getMaxStudents(), session.getRegistrations());
        }
        if (SessionType.FREE.getCode().equals(session.getTypeCode())) {
            return new FreeSession(session.getId(), session.getProgress(), session.getRecruitment(), session.getPeriod(), session.getCoverImages(), session.getMaxStudents(), session.getRegistrations());
        }
        throw new IllegalArgumentException("알 수 없는 세션 타입입니다: " + session.getTypeCode());
    }
}
