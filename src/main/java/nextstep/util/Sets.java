package nextstep.util;

import java.util.HashSet;
import java.util.Set;

public class Sets {
    private Sets() {
    }

    public static <T> Set<T> difference(Set<T> newItem, Set<T> oldItem) {
        Set<T> diff = new HashSet<>(newItem);
        diff.removeAll(oldItem);
        return diff;
    }
}
