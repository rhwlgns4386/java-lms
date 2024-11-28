package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public abstract class UserBaseEntity {
    private NsUser writer;

    public UserBaseEntity(){

    }

    public UserBaseEntity(NsUser writer) {
        this.writer = writer;
    }

    protected void validOwner(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw createException();
        }
    }

    public boolean isOwner(NsUser writer) {
        return this.writer.equals(writer);
    }

    public NsUser getWriter() {
        return writer;
    }

    protected abstract CannotDeleteException createException();
}
