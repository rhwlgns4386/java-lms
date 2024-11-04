package nextstep.courses.domain.dto;

import nextstep.courses.domain.session.SessionPriceType;
import nextstep.courses.domain.session.SessionStatus;

public class SessionDto {
    private Long sessionId;
    private String fileName;
    private int height;
    private int width;
    private int volume;
    private Long price;
    private SessionPriceType sessionPriceType;
    private SessionStatus sessionStatus;
    private String filePath;


    public SessionDto(Long sessionId, String fileName, int height, int width, int volume, Long price, SessionPriceType sessionPriceType, SessionStatus sessionStatus, String filePath) {
        this.sessionId = sessionId;
        this.fileName = fileName;
        this.height = height;
        this.width = width;
        this.volume = volume;
        this.price = price;
        this.sessionPriceType = sessionPriceType;
        this.sessionStatus = sessionStatus;
        this.filePath = filePath;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public String getFileName() {
        return fileName;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getVolume() {
        return volume;
    }

    public Long getPrice() {
        return price;
    }

    public SessionPriceType getSessionPriceType() {
        return sessionPriceType;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public String getFilePath() {
        return filePath;
    }

}
