package nextstep.courses.domain.common;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(Columns.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name();
    String subField() default "";
}
