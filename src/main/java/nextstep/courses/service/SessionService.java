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

@Transactional
@Service("sessionService")
public class SessionService {

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "sessionCoverImageRepository")
    private SessionCoverImageRepository sessionCoverImageRepository;

    @Resource(name = "enrollUserInfoRepository")
    private EnrollUserInfoRepository enrollUserInfoRepository;

    public void saveSession(SessionDto dto) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        Session session = dto.toModel();

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

    public void enroll(EnrollDto dto) {
        Session session = findById(dto.getId());

        session.enroll(dto.getNsUser(), dto.getPrice(), new PaymentStrategyImpl());

        for(EnrollUserInfo enrollUserInfo : session.getEnrollUserInfos()) {
            enrollUserInfoRepository.save(enrollUserInfo);
        }
    }

    @Transactional(readOnly = true)
    public Session findById(Long id) {
        return sessionRepository.findById(id);
    }

}
