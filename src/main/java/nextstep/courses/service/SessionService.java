package nextstep.courses.service;

import nextstep.courses.domain.dto.EnrollDto;
import nextstep.courses.domain.dto.SessionDto;
import nextstep.courses.domain.enroll.EnrollUserInfo;
import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.courses.domain.port.EnrollUserInfoRepository;
import nextstep.courses.domain.port.SessionCoverImageRepository;
import nextstep.courses.domain.port.SessionRepository;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.strategy.PaymentStrategyImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Set;

@Service("sessionService")
public class SessionService {

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "sessionCoverImageRepository")
    private SessionCoverImageRepository sessionCoverImageRepository;

    @Resource(name = "enrollUserInfoRepository")
    private EnrollUserInfoRepository enrollUserInfoRepository;

    @Transactional
    public void saveSession(SessionDto dto) {
        Session session = new Session.SessionBuilder()
                .sessionId(dto.getSessionId())
                .price(dto.getPrice())
                .sessionPriceType(dto.getSessionPriceType())
                .sessionStatus(dto.getSessionStatus())
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusMonths(3))
                .availableEnrollCount(30)
                .filePath(dto.getFilePath())
                .build();

        Long saveKey = sessionRepository.save(session);

        SessionCoverImage sessionCoverImage = new SessionCoverImage.SessionCoverImageBuilder()
                .sessionId(saveKey)
                .width(dto.getWidth())
                .height(dto.getHeight())
                .volume(dto.getVolume())
                .filePath(dto.getFilePath())
                .build();

        sessionCoverImageRepository.save(sessionCoverImage);
    }

    @Transactional
    public void enroll(EnrollDto dto) {
        Session session = findById(dto.getId());

        session.enroll(dto.getNsUser(), dto.getPrice(), new PaymentStrategyImpl());
        Set<EnrollUserInfo> enrollUserInfos = session.getEnrollUserInfos();

        for(EnrollUserInfo enrollUserInfo : enrollUserInfos) {
            enrollUserInfoRepository.save(enrollUserInfo);
        }
    }

    @Transactional(readOnly = true)
    public Session findById(Long id) {
        return sessionRepository.findById(id);
    }

}
