package nextstep.sessions.service;

import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.qna.CannotDeleteException;
import nextstep.qna.NotFoundException;
import nextstep.qna.domain.AnswerRepository;
import nextstep.qna.domain.DeleteHistory;
import nextstep.qna.domain.Question;
import nextstep.qna.domain.QuestionRepository;
import nextstep.qna.service.DeleteHistoryService;
import nextstep.sessions.domain.*;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@Service("sessionService")
public class SessionService {
    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "paymentService")
    private PaymentService paymentService;

    @Resource(name = "applicationDetailRepository")
    private ApplicationDetailRepository applicationDetailRepository;

    @Transactional
    public void apply(NsUser loginUser, long sessionId) throws CannotDeleteException {

        Session session = sessionRepository.findById(sessionId).orElseThrow(NotFoundException::new);
        List<ApplicationDetail> applicationDetails = applicationDetailRepository.findBySession(sessionId);
        session.addApplicationDetails(applicationDetails);

        Optional<Payment> payment = payForSession(session);

        session.apply(loginUser, payment);

        applicationDetailRepository.save(session.getApplicationDetail(loginUser.getId(), sessionId));
    }

    private Optional<Payment> payForSession(Session session) {
        if (session.isFree()) {
            return Optional.empty();
        }
        return Optional.of(paymentService.payment("201"));
    }

}
