package nextstep.courses.infrastructure;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.LimitedEnrollments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.factory.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcSessionDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSessionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    Optional<Session> findById(Long id, Set<EnrollmentStudent> enrollmentStudents) {
        String sql = "select id, charge, capacity, sessionStatus, image_file_name, image_width, image_height,"
                + "image_size, imageType, start_date, end_date from session where id = ?";

        RowMapper<Session> rowMapper = ((rs, rowNum) -> SessionFactory.session(
                rs.getLong(1),
                rs.getInt(2),
                enrollments((Integer) rs.getObject(3),
                        SessionStatus.findByName(rs.getString(4)),
                        enrollmentStudents),
                rs.getString(5),
                rs.getInt(6),
                rs.getInt(7),
                rs.getInt(8),
                ImageType.findByName(rs.getString(9)),
                toLocalDate(rs.getDate(10)),
                toLocalDate(rs.getDate(11)))
        );

        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private Enrollments enrollments(Integer capacity, SessionStatus sessionStatus,
                                    Set<EnrollmentStudent> enrolledStudents) {
        if (capacity == null) {
            return new Enrollments(sessionStatus, enrolledStudents);
        }

        return new LimitedEnrollments(capacity, sessionStatus, enrolledStudents);
    }

    private LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }

    void update(Session session) {
        SessionHelper helper = new SessionHelper(session);
        String sql = "update session set charge=?,capacity=?,sessionStatus=?,image_file_name=?,image_width=?,"
                + "image_height=?,image_size=?,imageType=?,start_date=?,end_date =? where id=?";

        jdbcTemplate.update(sql,
                helper.getCharge(),
                helper.getCapacity(),
                helper.getSessionStatus(),
                helper.getImageFileName(),
                helper.getImageWidth(),
                helper.getImageHeight(),
                helper.getImageBytes(),
                helper.getImageType(),
                helper.getStartDate(),
                helper.getEndDate(),
                helper.getId()
        );
    }

}
