package nextstep.qna.domain;

import static nextstep.qna.domain.DeleteHistoryFactory.createDeleteHistory;

import java.util.Optional;
import nextstep.qna.CannotDeleteException;
import nextstep.qna.NotFoundException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Answer extends BaseEntity {

    private DeleteRule deleteRule;
    private Question question;
    private String contents;

    public Answer() {
    }

    public Answer(NsUser writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, NsUser writer, Question question, String contents) {
        this(id, Optional.ofNullable(writer), Optional.ofNullable(question), contents);
    }

    private Answer(Long id, Optional<NsUser> writer, Optional<Question> question, String contents) {
        this(id, new DeleteRule(writer.orElseThrow(UnAuthorizedException::new), "다른 사람이 쓴 답변이 있어 삭제할 수 없습니다."),
                question, contents);
    }

    private Answer(Long id, DeleteRule deleteRule, Optional<Question> question, String contents) {
        super(id);
        this.deleteRule = deleteRule;
        this.question = question.orElseThrow(NotFoundException::new);
        this.contents = contents;
    }

    public final DeleteHistory delete(NsUser loginUser) throws CannotDeleteException {
        return deleteRule.delete(loginUser, ContentType.ANSWER, getId());
    }

    public NsUser getWriter() {
        return deleteRule.getWriter();
    }

    public String getContents() {
        return contents;
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public boolean isDeleted() {
        return deleteRule.isDeleted();
    }

    @Override
    public String toString() {
        return "Answer [id=" + getId() + ", writer=" + deleteRule.getWriter() + ", contents=" + contents + "]";
    }

}
