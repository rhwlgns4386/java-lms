package nextstep.courses.domain;

import java.util.Random;

public class RandomSelection implements SelectionStrategy {
    public static Random RANDOM = new Random();

    @Override
    public boolean select() {
        return RANDOM.nextBoolean();
    }
}
