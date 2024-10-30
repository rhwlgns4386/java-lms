package nextstep.qna.domain;

public class QuestionBoard {
    private String title;

    private String contents;

    public QuestionBoard(String title) {
        this(title, null);
    }

    public QuestionBoard(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}
