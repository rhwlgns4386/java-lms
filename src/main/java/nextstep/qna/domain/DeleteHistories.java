package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;

public class DeleteHistories {
    private List<DeleteHistory> deleteHistories;

    public DeleteHistories(List<DeleteHistory> deleteHistories) {
        this.deleteHistories = deleteHistories;
    }

    public DeleteHistories() {
        this.deleteHistories = new ArrayList<>();
    }

    public void add(DeleteHistory deleteHistory) {
        this.deleteHistories.add(deleteHistory);
    }

    public void add(DeleteHistories inputDeleteHistories) {
        this.deleteHistories.addAll(inputDeleteHistories.deleteHistories);
    }

    public List<DeleteHistory> asList() {
        return deleteHistories;
    }
}
