package nextstep.courses.service;


import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.courses.domain.coverimage.SessionCoverImagePath;
import nextstep.courses.domain.coverimage.SessionCoverImageSize;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionPayType;
import nextstep.courses.dto.MultipartFile;
import nextstep.courses.dto.SessionDto;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sessionService")
public class SessionService {

    @Transactional
    public void saveSession(SessionDto dto){
        MultipartFile multipartFile = dto.getMultipartFile();

        SessionCoverImage sessionCoverImage = new SessionCoverImage(
                multipartFile.getSize(),
                new SessionCoverImagePath("/",multipartFile.getOriginalFileName()),
                new SessionCoverImageSize(multipartFile.getWidth(),multipartFile.getHeight())
        );

        Session session = new Session(
                sessionCoverImage,
                SessionPayType.search(dto.getPayType()),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getSessionPay(),
                dto.getMaximumNumberPeople()
        );
    }

    @Transactional
    public void registration(NsUser nsUser, Payment payment){
        // 다음 과제 시
    }


}
