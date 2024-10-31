package nextstep.sessions.domain;

public interface SessionImageRepository {
    SessionImage save(SessionImage image);
    SessionImage findById(Long imageId);
}
