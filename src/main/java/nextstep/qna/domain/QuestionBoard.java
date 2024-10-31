package nextstep.qna.domain;

public class QuestionBoard {
    private String title;

    private String contents;

    public QuestionBoard(String title) {
        this(title, null);
    }

    public QuestionBoard(String title, String contents) {
        validateBoardCheck(title, contents);
        this.title = title;
        this.contents = contents;

    }

    private void validateBoardCheck(String title, String contents) {
        if(title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}
