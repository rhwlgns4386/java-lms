package nextstep.users.domain;

import java.time.LocalDateTime;

public class NsUserTest {
    public static final NsUser JAVAJIGI = new NsUser(1L, "javajigi", "password", "name", "javajigi@slipp.net", LocalDateTime.now(), LocalDateTime.now());
    public static final NsUser SANJIGI = new NsUser(2L, "sanjigi", "password", "name", "sanjigi@slipp.net", LocalDateTime.now(), LocalDateTime.now());
    public static final NsUser NOT_SELECTED = new NsUser(3L, "notSelected", "password", "name", "test@slipp.net", LocalDateTime.now(), LocalDateTime.now());
}
