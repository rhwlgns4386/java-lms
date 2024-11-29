package nextstep.qna.domain;

public class QuestionBody {
    private final String title;
    private final String contents;

    public QuestionBody(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public final String getTitle() {
        return title;
    }

    public final String getContents() {
        return contents;
    }

    public final QuestionBody updateContents(String contents) {
        return questionBody(this.title, contents);
    }

    public final QuestionBody updateTitle(String title) {
        return questionBody(title, this.contents);
    }

    private QuestionBody questionBody(String title, String contents) {
        return new QuestionBody(title, contents);
    }
}
