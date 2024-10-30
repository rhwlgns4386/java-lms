package nextstep.courses.domain.session;

@FunctionalInterface
public interface StudentCapacity {

    boolean isApplicable(int count);

}
