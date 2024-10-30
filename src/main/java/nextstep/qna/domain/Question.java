package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private Long id;

    private QuestionBoard questionBoard;

    private NsUser writer;

    private Answers answers;

    private boolean deleted = false;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.questionBoard = new QuestionBoard(title, contents);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return questionBoard.getTitle();
    }

    public String getContents() {
        return questionBoard.getContents();
    }

    public Question setQuestionBoard(String title, String contents) {
        this.questionBoard = new QuestionBoard(title, contents);
        return this;
    }

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        //List<Answer> newAnswers = new ArrayList<>();
        //newAnswers.add(answer);
        if(answers == null) {
            answers = new Answers(new ArrayList<>());
        }
        answers.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<Answer> getAnswers() {
        return answers.getAnswers();
    }

    public void deleteQuestion(NsUser loginUser) throws CannotDeleteException {
        isOwnerCheck(loginUser);
        this.deleted = true;
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + questionBoard.getTitle() + ", contents=" + questionBoard.getContents() + ", writer=" + writer + "]";
    }

    public void isOwnerCheck(NsUser loginUser) throws CannotDeleteException {
        if (!this.isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }
}
