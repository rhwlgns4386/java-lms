package nextstep.qna.domain;

import java.util.function.Supplier;
import nextstep.users.domain.NsUser;

public class Writer {
    private NsUser writer;

    public Writer(NsUser writer) {
        this.writer = writer;
    }

    public  <T extends  Throwable> void validOwner(NsUser loginUser, Supplier<T> exceptionSupplier) throws T {
        if (!isWriter(loginUser)) {
            throw exceptionSupplier.get();
        }
    }

    private boolean isWriter(NsUser writer) {
        return this.writer.equals(writer);
    }

    public NsUser toUser() {
        return writer;
    }
}
