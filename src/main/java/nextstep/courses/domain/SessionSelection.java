package nextstep.courses.domain;

public enum SessionSelection {
    RANDOM_SELECTION(RandomSelection.getInstance()),
    ALL_SELECT(() -> true),
    ALL_UNSELECT(() -> false);
    private final SelectionStrategy strategy;

    SessionSelection(SelectionStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean select() {
        return this.strategy.select();
    }
}
