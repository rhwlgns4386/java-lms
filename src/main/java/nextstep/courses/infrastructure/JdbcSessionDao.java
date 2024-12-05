package nextstep.courses.infrastructure;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.EnrollmentsFactory;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.factory.ImageConverter;
import nextstep.courses.factory.SessionFactory;
import nextstep.courses.factory.SessionPeriodConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcSessionDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcSessionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public final Optional<Session> findById(Long id, List<CoverImage> coverImages) {
        String sql = "select id, charge, capacity, sessionStatus, start_date, end_date from session where id = ?";

        RowMapper<Session> rowMapper = ((rs, rowNum) -> SessionFactory.session(
                rs.getLong(1),
                rs.getInt(2),
                (Integer) rs.getObject(3),
                SessionStatus.findByName(rs.getString(4)),
                enrollmentFactory(),
                coverImages,
                toPeriod(toLocalDate(rs.getDate(5)),
                        toLocalDate(rs.getDate(6))))
        );

        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private Image toImage(String fileName, int width, int height, int size, ImageType imageType) {
        return ImageConverter.toImage(fileName, width, height, size, imageType);
    }

    private SessionPeriod toPeriod(LocalDate startDate, LocalDate endDate) {
        return SessionPeriodConverter.toSessionPeriod(startDate, endDate);
    }

    protected EnrollmentsFactory enrollmentFactory() {
        return new EnrollmentsFactory();
    }


    private LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }

    public final void update(Session session) {
        SessionHelper helper = new SessionHelper(session);
        String sql = "update session set charge=?,capacity=?,sessionStatus=?,start_date=?,end_date =? where id=?";

        jdbcTemplate.update(sql,
                helper.getCharge(),
                helper.getCapacity(),
                helper.getSessionStatus(),
                helper.getStartDate(),
                helper.getEndDate(),
                helper.getId()
        );
    }
}
