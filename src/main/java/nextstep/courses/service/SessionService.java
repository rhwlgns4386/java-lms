package nextstep.courses.service;


import nextstep.courses.domain.SessionApplyRepository;
import nextstep.courses.domain.SessionCoverImageRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStudentRepository;
import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.courses.domain.coverimage.SessionCoverImagePath;
import nextstep.courses.domain.coverimage.SessionCoverImageSize;
import nextstep.courses.domain.coverimage.SessionCoverImages;
import nextstep.courses.domain.session.*;
import nextstep.courses.dto.MultipartFile;
import nextstep.courses.dto.SessionDto;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("sessionService")
public class SessionService {

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "sessionCoverImageRepository")
    private SessionCoverImageRepository sessionCoverImageRepository;

    @Resource(name = "sessionStudentRepository")
    private SessionStudentRepository sessionStudentRepository;

    @Resource(name = "sessionApplyRepository")
    private SessionApplyRepository sessionApplyRepository;


    @Transactional
    public void saveSession(SessionDto dto) {

        List<MultipartFile> multipartFiles = dto.getMultipartFiles();

        Session createdSession = createSessionPayType(dto);
        Long sessionId = sessionRepository.save(createdSession);

        SessionCoverImages sessionCoverImages = SessionCoverImages.create(sessionId, multipartFiles);

        sessionCoverImageRepository.saveAll(sessionCoverImages);
    }

    private Session createSessionPayType(SessionDto dto) {
        if (SessionPayType.search(dto.getPayType()) == SessionPayType.FREE) {
            return Session.createFree(dto.getCourseId(), new SessionPeriod(dto.getStartDate(), dto.getEndDate()));
        }
        return Session.createPaid(dto.getCourseId(),
                dto.getSessionPay(),
                new SessionPeriod(dto.getStartDate(), dto.getEndDate()),
                dto.getMaximumNumberPeople());
    }

    @Transactional
    public void registration(NsUser nsUser, Payment payment) {
        Long sessionId = payment.getSessionId();

        Session session = sessionRepository.findById(sessionId);
        List<SessionStudent> students = sessionStudentRepository.findBySessionId(payment.getSessionId());
        session.mapping(students);

        session.registration(nsUser, payment);

        SessionApply apply = SessionApply.create(sessionId, nsUser.getId());
        sessionApplyRepository.save(apply);
    }

    @Transactional
    public void cancel(Long applyId) {
        SessionApply apply = sessionApplyRepository.findById(applyId);
        apply.cancel();
        sessionApplyRepository.update(apply);
    }

    @Transactional
    public void submit(Long applyId) {
        SessionApply apply = sessionApplyRepository.findById(applyId);
        apply.submit();
        sessionApplyRepository.update(apply);
    }
}
