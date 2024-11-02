package nextstep.courses.service;


import nextstep.courses.domain.SessionCoverImageRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStudentRepository;
import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.courses.domain.coverimage.SessionCoverImagePath;
import nextstep.courses.domain.coverimage.SessionCoverImageSize;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionPayType;
import nextstep.courses.domain.session.SessionPeriod;
import nextstep.courses.domain.session.SessionStudent;
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

    @Transactional
    public void saveSession(SessionDto dto) {

        MultipartFile multipartFile = dto.getMultipartFile();

        Session createdSession = createSessionPayType(dto);
        Long sessionId = sessionRepository.save(createdSession);

        SessionCoverImage image = SessionCoverImage.create(
                sessionId,
                multipartFile.getSize(),
                SessionCoverImagePath.create("/", multipartFile.getOriginalFileName()),
                new SessionCoverImageSize(multipartFile.getWidth(), multipartFile.getHeight())
        );

        sessionCoverImageRepository.save(image);
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

        SessionStudent registration = session.registration(nsUser, payment);

        sessionStudentRepository.save(registration);
    }


}
