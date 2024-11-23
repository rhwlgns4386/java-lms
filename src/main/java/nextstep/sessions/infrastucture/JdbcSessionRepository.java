package nextstep.sessions.infrastucture;

import nextstep.courses.domain.Course;
import nextstep.sessions.domain.*;
import nextstep.studentsessions.domain.RegistrationApprovalStatus;
import nextstep.studentsessions.domain.StudentSession;
import nextstep.users.domain.NsStudent;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<PaidSession> findPaidSessionById(Long sessionId) {
        return Optional.ofNullable(jdbcTemplate.query(findSessionByIdQuery(), rs -> {
            PaidSession session = null;
            int row = 0;
            Course course = null;
            List<Image> coverImages = new ArrayList<>();
            List<StudentSession> studentSessions = new ArrayList<>();
            while (rs.next()) {
                if (session == null) {
                    session = paidSessionRowMapper().mapRow(rs, row);
                }
                if (session != null && course == null) {
                    course = courseRowMapper().mapRow(rs, row);
                }
                if (session != null) {
                    final Image coverImage = sessionImageRowMapper().mapRow(rs, row);
                    coverImages.add(coverImage);

                    final StudentSession studentSession = studentSessionRowMapper(session).mapRow(rs, row);
                    studentSessions.add(studentSession);
                    row++;
                }
            }
            if (session != null) {
                session.setRelatedEntities(course, coverImages, studentSessions);
            }
            return session;
        }, sessionId));
    }

    @Override
    public Optional<FreeSession> findFreeSessionById(Long sessionId) {
        return Optional.ofNullable(jdbcTemplate.query(findSessionByIdQuery(), rs -> {
            FreeSession session = null;
            int row = 0;
            Course course = null;
            List<Image> coverImages = new ArrayList<>();
            List<StudentSession> studentSessions = new ArrayList<>();
            while (rs.next()) {
                if (session == null) {
                    session = freeSessionRowMapper().mapRow(rs, row);
                }
                if (session != null && course == null) {
                    course = courseRowMapper().mapRow(rs, row);
                }
                if (session != null) {
                    final Image coverImage = sessionImageRowMapper().mapRow(rs, row);
                    coverImages.add(coverImage);

                    final StudentSession studentSession = studentSessionRowMapper(session).mapRow(rs, row);
                    studentSessions.add(studentSession);
                    row++;
                }
            }
            if (session != null) {
                session.setRelatedEntities(course, coverImages, studentSessions);
            }
            return session;
        }, sessionId));
    }

    private String findSessionByIdQuery() {
        return "SELECT s.id AS session_id, s.title AS session_title, s.fee AS session_fee, s.max_student AS session_max_student, s.start_date AS session_start_date, s.end_date AS session_end_date, s.session_status, " +
                "s.recruitment_status AS session_recruitment_state, s.created_at AS session_created_at, s.updated_at AS session_updated_at," +
                "c.id AS course_id, c.title AS course_title, c.creator_id AS course_creator_id, s.cohort AS course_cohort, s.created_at AS course_created_at, s.updated_at AS course_updated_at," +
                "us.id AS user_id, us.user_id AS user_userid, us.session_id, us.approval_status AS session_approval_status, us.created_at AS user_created_at, us.updated_at AS user_created_at," +
                "img.id AS image_id, img.session_id, img.image_type AS image_type, img.size_in_kb AS image_size_in_kb, img.width_in_pixels AS image_width_in_pixels, img.height_in_pixels AS image_height_in_pixels, " +
                "img.created_at AS image_created_at, img.updated_at AS image_updated_at\n" +
                "FROM sessions s\n" +
                "JOIN course c ON s.course_id = c.id\n" +
                "LEFT JOIN user_session us ON s.id = us.session_id\n" +
                "LEFT JOIN image img ON s.id = img.session_id\n" +
                "WHERE s.id = ?";
    }

    private RowMapper<FreeSession> freeSessionRowMapper() {
        return ((rs, rowNum) -> new FreeSession(
                rs.getLong("session_id"),
                null, new ArrayList<>(), rs.getString("session_title"),
                new ArrayList<>(),
                rs.getObject("session_status", SessionProgressStatus.class),
                rs.getObject("session_recruitment_state", SessionRecruitmentStatus.class),
                toLocalDate(rs.getDate("session_start_date")), toLocalDate(rs.getDate("session_end_date")),
                toLocalDateTime(rs.getTimestamp("session_created_at")),
                toLocalDateTime(rs.getTimestamp("session_updated_at"))
        ));
    }

    private RowMapper<PaidSession> paidSessionRowMapper() {
        return ((rs, rowNum) -> new PaidSession(
                rs.getLong("session_id"),
                null, new ArrayList<>(), rs.getString("session_title"),
                rs.getInt("session_fee"),
                new ArrayList<>(),
                rs.getInt("session_max_student"), rs.getObject("session_status", SessionProgressStatus.class),
                rs.getObject("session_recruitment_state", SessionRecruitmentStatus.class),
                toLocalDate(rs.getDate("session_start_date")), toLocalDate(rs.getDate("session_end_date")),
                toLocalDateTime(rs.getTimestamp("session_created_at")),
                toLocalDateTime(rs.getTimestamp("session_updated_at"))
        ));
    }

    private RowMapper<Course> courseRowMapper() {
        return (rs, rowNum) -> new Course(
                rs.getLong("course_id"),
                rs.getString("course_title"),
                rs.getLong("course_creator_id"),
                rs.getInt("course_cohort"),
                toLocalDateTime(rs.getTimestamp("course_created_at")),
                toLocalDateTime(rs.getTimestamp("course_updated_at"))
        );
    }

    private RowMapper<StudentSession> studentSessionRowMapper(Session session) {
        return (rs, rowNum) -> new StudentSession(
                session,
                new NsStudent(rs.getLong("user_id"),
                        rs.getString("user_userid"),
                        rs.getString("user_password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        null,
                        toLocalDateTime(rs.getTimestamp("user_created_at")),
                        toLocalDateTime(rs.getTimestamp("user_updated_at"))
                ),
                rs.getObject("session_approval_status", RegistrationApprovalStatus.class),
                toLocalDateTime(rs.getTimestamp("user_session_created_at")),
                toLocalDateTime(rs.getTimestamp("user_session_updated_at"))
        );
    }

    private RowMapper<Image> sessionImageRowMapper() {
        return (rs, rowNum) -> new Image(
                rs.getLong("image_id"),
                rs.getInt("image_size_in_kb"),
                rs.getString("image_type"),
                rs.getInt("image_width_in_pixels"),
                rs.getInt("image_height_in_pixels"),
                toLocalDateTime(rs.getTimestamp("image_created_at")),
                toLocalDateTime(rs.getTimestamp("image_updated_at"))
        );
    }

    protected static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    protected static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }
}
