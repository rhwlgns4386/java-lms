package nextstep.qna.domain.question;

import nextstep.qna.domain.DeleteHistory.DeleteHistory;
import nextstep.qna.domain.answer.Comments;
import nextstep.qna.domain.answer.Answer;
import nextstep.qna.domain.answer.Answers;
import nextstep.users.domain.NsUser;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class QuestionContents {
    private final String title;
    private final Comments comments;
    private final Answers answers;

    public QuestionContents(NsUser user, String title, String contents, List<Answer> answers) {
        this(title, new Comments(user, contents), new Answers(answers));
    }

    public QuestionContents(String title, Comments comments, Answers answers) {
        this.title = title;
        this.comments = comments;
        this.answers = answers;
    }

    public boolean isOwner(NsUser loginUser) {
        return this.comments.isOwner(loginUser);
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public void deleteAnswer(NsUser loginUser) {
        this.answers.deleteAnswer(loginUser);
    }

    public List<DeleteHistory> toDeleteHistories() {
        return this.answers.toDeleteHistories();
    }

    public NsUser getWriter() {
        return this.comments.getWriter();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionContents)) return false;
        QuestionContents that = (QuestionContents) o;
        return Objects.equals(title, that.title) && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, comments);
    }
}
