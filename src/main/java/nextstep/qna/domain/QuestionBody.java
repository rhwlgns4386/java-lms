package nextstep.qna.domain;

public class QuestionBody {
    private final String title;
    private final String contents;

    public QuestionBody(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public QuestionBody updateContents(String contents) {
        return questionBody(this.title, contents);
    }

    public QuestionBody updateTitle(String title) {
        return questionBody(title, this.contents);
    }

    private QuestionBody questionBody(String title, String contents) {
        return new QuestionBody(title, contents);
    }
}
