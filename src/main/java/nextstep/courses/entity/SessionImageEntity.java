package nextstep.courses.entity;

import nextstep.courses.domain.SessionImage;

public class SessionImageEntity {
    private Long id;
    private Integer volume;
    private String type;
    private Integer width;
    private Integer height;
    private Long sessionId;

    public SessionImageEntity() {
    }

    public SessionImageEntity(Long id, Integer volume, String type, Integer width, Integer height) {
        this.id = id;
        this.volume = volume;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public static SessionImageEntity from(SessionImage sessionImage, Long sessionId) {
        SessionImageEntity sessionImageEntity = new SessionImageEntity();
        sessionImageEntity.id = sessionImage.getId();
        sessionImageEntity.volume = sessionImage.getVolume();
        sessionImageEntity.type = sessionImage.getType().name();
        sessionImageEntity.width = sessionImage.getSessionImageSize().getWidth();
        sessionImageEntity.height = sessionImage.getSessionImageSize().getHeight();
        sessionImageEntity.sessionId = sessionId;
        return sessionImageEntity;
    }

    public SessionImage to() {
        return new SessionImage(id, volume, type, width, height);
    }

    public Long getId() {
        return id;
    }

    public Integer getVolume() {
        return volume;
    }

    public String getType() {
        return type;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Long getSessionId() {
        return sessionId;
    }
}
