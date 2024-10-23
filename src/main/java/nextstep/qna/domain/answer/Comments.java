package nextstep.qna.domain.answer;


import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

import java.util.Objects;

public class Comments {
    private final String contents;
    private final NsUser writer;

    public Comments(NsUser writer, String contents) {
        if(writer == null) {
            throw new UnAuthorizedException();
        }
        this.writer = writer;

        this.contents = contents;
    }

    public boolean isOwner(NsUser writer) {
        return this.writer.equals(writer);
    }


    public NsUser getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comments)) return false;
        Comments contents1 = (Comments) o;
        return Objects.equals(contents, contents1.contents) && Objects.equals(writer, contents1.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents, writer);
    }
}
