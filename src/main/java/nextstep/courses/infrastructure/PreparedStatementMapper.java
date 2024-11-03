package nextstep.courses.infrastructure;

import nextstep.courses.domain.common.Column;
import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.session.*;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class PreparedStatementMapper {
    private static final String SESSION_TYPE = "session_type";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String MAX_STUDENTS = "maxStudents";
    private PreparedStatementMapper() {
    }

    public static <T> void mapParameters(PreparedStatement ps, T entity) throws SQLException {
        Class<?> clazz = entity.getClass();
        List<Field> fields = getAllFields(clazz);

        List<ColumnMapping> mappings = fields.stream()
                .flatMap(field -> Arrays.stream(field.getAnnotationsByType(Column.class))
                        .map(column -> new ColumnMapping(field, column)))
                .sorted(Comparator.comparing(mapping -> getSqlIndex(mapping.column.name())))
                .collect(Collectors.toList());

        for (ColumnMapping mapping : mappings) {
            mapField(ps, entity, mapping.field, mapping.column);
        }
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static Integer getSqlIndex(String columnName) {
        Map<String, Integer> sqlOrder = Map.of(
                "status", 1,
                "start_date", 2,
                "end_date", 3,
                "cover_image_id", 4,
                "session_type", 5,
                "course_fee", 6,
                "max_students", 7
        );
        return sqlOrder.getOrDefault(columnName, Integer.MAX_VALUE);
    }

    private static <T> void mapField(PreparedStatement ps, T entity, Field field, Column column) throws SQLException {
        try {
            field.setAccessible(true);
            Object value = field.get(entity);
            int parameterIndex = getSqlIndex(column.name());

            if (SESSION_TYPE.equals(column.name())) {
                String sessionType = entity instanceof PaidSession ?
                        SessionType.PAID.getCode() : SessionType.FREE.getCode();
                setParameter(ps, parameterIndex, sessionType);
                return;
            }

            if (value == null) {
                ps.setNull(parameterIndex, getSqlType(field.getType()));
                return;
            }

            if (value instanceof Period) {
                Period period = (Period) value;
                if (START_DATE.equals(column.subField())) {
                    setParameter(ps, parameterIndex, period.getStartDate());
                } else if (END_DATE.equals(column.subField())) {
                    setParameter(ps, parameterIndex, period.getEndDate());
                }
                return;
            }

            if (value instanceof Capacity && MAX_STUDENTS.equals(column.subField())) {
                Capacity capacity = (Capacity) value;
                setParameter(ps, parameterIndex, capacity.getMaxStudentsSize());
                return;
            }

            if (value instanceof CoverImage) {
                setParameter(ps, parameterIndex, ((CoverImage) value).getId());
                return;
            }

            if (value instanceof Money) {
                setParameter(ps, parameterIndex, ((Money) value).getAmount());
                return;
            }

            if (value instanceof Status) {
                setParameter(ps, parameterIndex, ((Status) value).getCode());
                return;
            }

            setParameter(ps, parameterIndex, value);

        } catch (IllegalAccessException e) {
            throw new SQLException("필드를 찾지 못했습니다." + field.getName(), e);
        }
    }

    private static void setParameter(PreparedStatement ps, int index, Object value) throws SQLException {
        if (value == null) {
            ps.setNull(index, Types.NULL);
            return;
        }
        if (value instanceof String) {
            ps.setString(index, (String) value);
            return;
        }
        if (value instanceof Long) {
            ps.setLong(index, (Long) value);
            return;
        }
        if (value instanceof Integer) {
            ps.setInt(index, (Integer) value);
            return;
        }
        if (value instanceof LocalDate) {
            ps.setTimestamp(index, Timestamp.valueOf(((LocalDate) value).atStartOfDay()));
            return;
        }
        throw new SQLException("지원하지 않는 타입입니다: " + value.getClass());

    }

    private static int getSqlType(Class<?> type) {
        if (type == String.class) {
            return Types.VARCHAR;
        }
        if (type == Long.class) {
            return Types.BIGINT;
        }
        if (type == Integer.class) {
            return Types.INTEGER;
        }
        if (type == LocalDate.class) {
            return Types.TIMESTAMP;
        }
        return Types.NULL;
    }

    private static class ColumnMapping {
        final Field field;
        final Column column;

        ColumnMapping(Field field, Column column) {
            this.field = field;
            this.column = column;
        }
    }
}
