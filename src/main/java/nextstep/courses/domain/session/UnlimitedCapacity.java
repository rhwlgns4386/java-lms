package nextstep.courses.domain.session;

public class UnlimitedCapacity implements StudentCapacity {

    @Override
    public boolean isApplicable(int count) {
        return true;
    }

}
