package nextstep.qna.domain;

import java.time.LocalDateTime;
import nextstep.users.domain.NsUser;

public class DeleteHistoryFactory {

    static DeleteHistory createDeleteHistory(ContentType contentType, long id, NsUser writer) {
        return new DeleteHistory(contentType, id, writer, LocalDateTime.now());
    }
}
