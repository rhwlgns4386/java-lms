package nextstep.courses.domain;

public class UnlimitedCapacity implements StudentCapacity {

    @Override
    public boolean isApplicable(int count) {
        return true;
    }

}
