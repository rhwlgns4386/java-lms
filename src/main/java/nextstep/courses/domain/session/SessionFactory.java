package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;

import java.util.List;

public class SessionFactory {
    private SessionFactory() {
    }

    public static DefaultSession createSession(
            SessionType sessionType,
            Long id,
            SessionProgress progress,
            SessionRecruitmentStatus recruitment,
            Period period,
            List<CoverImage> coverImages,
            Money courseFee,
            int maxStudents,
            List<SessionRegistration> registrations
    ) {
        if (SessionType.PAID.getCode().equals(sessionType.getCode())) {
            return new PaidSession(id, progress, recruitment, period, coverImages, courseFee, maxStudents, registrations);
        }
        if (SessionType.FREE.getCode().equals(sessionType.getCode())) {
            return new FreeSession(id, progress, recruitment, period, coverImages, maxStudents, registrations);
        }
        throw new IllegalArgumentException("알 수 없는 세션 타입입니다: " + sessionType);
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
