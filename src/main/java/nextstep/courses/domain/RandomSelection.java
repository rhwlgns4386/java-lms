package nextstep.courses.domain;

import java.util.Random;

public class RandomSelection implements SelectionStrategy {
    public static Random RANDOM = new Random();
    public static RandomSelection INSTANCE = null;

    private RandomSelection() {
    }

    public static RandomSelection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RandomSelection();
        }
        return INSTANCE;
    }

    @Override
    public boolean select() {
        return RANDOM.nextBoolean();
    }
}
