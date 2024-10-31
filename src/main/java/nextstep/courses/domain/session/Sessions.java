package nextstep.courses.domain.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Sessions {
    private final List<Session> sessoins;

    public Sessions() {
        this(new ArrayList<>());
    }

    public Sessions(List<Session> sessoins) {
        this.sessoins = sessoins;
    }

    public void add(Session session) {
        sessoins.add(session);
    }

    public List<Session> getSessoins() {
        return Collections.unmodifiableList(sessoins);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sessions sessions = (Sessions) o;
        return Objects.equals(sessoins, sessions.sessoins);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessoins);
    }

}
