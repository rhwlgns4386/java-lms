package nextstep.courses.domain.coverimage;

import nextstep.courses.dto.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

public class SessionCoverImages {

    private List<SessionCoverImage> coverImages;

    private SessionCoverImages(List<SessionCoverImage> coverImages) {
        this.coverImages = coverImages;
    }

    public static SessionCoverImages create(Long sessionId, List<MultipartFile> multipartFiles) {
        List<SessionCoverImage> collect = multipartFiles.stream().map(file -> SessionCoverImage.create(sessionId,
                file.getSize(),
                SessionCoverImagePath.create("/", file.getOriginalFileName()),
                new SessionCoverImageSize(file.getWidth(), file.getHeight())
        )).collect(Collectors.toList());
        return new SessionCoverImages(collect);
    }

    public SessionCoverImage get(int index){
        return coverImages.get(index);
    }
    public int getSize(){
        return coverImages.size();
    }
}
