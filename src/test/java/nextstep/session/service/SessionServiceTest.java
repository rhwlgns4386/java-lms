package nextstep.session.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nextstep.users.domain.NsUserTest;

@SpringBootTest
class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Test
    @DisplayName("강의 수강신청 시 정상 등록 된다.")
    void throwExceptionWhenSessionFeeDoesNotMatchPaymentAmount() {
        assertDoesNotThrow(() -> sessionService.enroll(NsUserTest.JAVAJIGI, 2L));
    }
}