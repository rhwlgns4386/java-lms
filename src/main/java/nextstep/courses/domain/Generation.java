package nextstep.courses.domain;

import java.util.Objects;

public class Generation {
    public static final int INIT_GENERATION = 1;
    private final int generation;

    public Generation() {
        this.generation = INIT_GENERATION;
    }

    public Generation(int generation) {
        if (generation < INIT_GENERATION) {
            throw new IllegalArgumentException("기수는" + INIT_GENERATION + "이상이어야 합니다.");
        }
        this.generation = generation;
    }

    public int getGeneration() {
        return generation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Generation that = (Generation) o;
        return generation == that.generation;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(generation);
    }
}
