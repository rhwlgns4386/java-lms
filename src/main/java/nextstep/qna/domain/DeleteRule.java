package nextstep.qna.domain;

import static nextstep.qna.domain.DeleteHistoryFactory.createDeleteHistory;

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

    public DeleteHistory delete(NsUser loginUser, ContentType contentType, Long id) throws CannotDeleteException {
        writer.validOwner(loginUser,this::createException);
        this.deleted=true;
        return  createDeleteHistory(contentType, id, writer.toUser());
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
