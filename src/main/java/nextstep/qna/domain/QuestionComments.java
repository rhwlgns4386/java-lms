package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.util.Objects;

public class QuestionComments {
    private final String title;
    private final String comments;
    private final NsUser writer;

    public QuestionComments(String title, String comments, NsUser writer) {
        this.title = title;
        this.comments = comments;
        this.writer = writer;
    }

    public NsUser getWriter() {
        return this.writer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionComments)) return false;
        QuestionComments that = (QuestionComments) o;
        return Objects.equals(title, that.title) && Objects.equals(comments, that.comments) && Objects.equals(writer, that.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, comments, writer);
    }
}
