package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class DeleteRule {
    private boolean deleted = false;
    private Writer writer;
    private String message;

    public DeleteRule(Writer writer, String message) {
        this.writer = writer;
        this.message = message;
    }

    public DeleteRule(NsUser user, String message) {
        this(new Writer(user), message);
    }

    public void delete(NsUser loginUser) throws CannotDeleteException {
        writer.validOwner(loginUser,this::createException);
        this.deleted=true;
    }

    private CannotDeleteException createException() {
        return new CannotDeleteException(message);
    }

    public NsUser getWriter() {
        return writer.toUser();
    }

    public boolean isDeleted() {
        return deleted;
    }
}
