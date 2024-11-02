package nextstep.courses.service;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.image.ImageRepository;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sessionService")
public class SessionService {
    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;
    @Resource(name = "imageRepository")
    private ImageRepository imageRepository;
    @Resource(name = "studentRepository")
    private StudentRepository studentRepository;

    @Transactional
    public long create(Long courseId, Session session, Image image) {
        long saveSessionId = sessionRepository.save(session, courseId);
        imageRepository.save(image, saveSessionId);

        return saveSessionId;
    }
}
