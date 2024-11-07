package nextstep.courses.domain.dto;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionPriceType;
import nextstep.courses.domain.session.SessionStatus;

import java.time.LocalDateTime;

public class SessionDto {
    private final Long sessionId;
    private final String fileName;
    private final int height;
    private final int width;
    private final int volume;
    private final Long price;
    private final SessionPriceType sessionPriceType;
    private final SessionStatus sessionStatus;
    private final String filePath;
    private final int availableEnrollCount;

    private SessionDto(Long sessionId, String fileName, int height, int width, int volume, Long price, SessionPriceType sessionPriceType, SessionStatus sessionStatus, String filePath, int availableEnrollCount) {
        this.sessionId = sessionId;
        this.fileName = fileName;
        this.height = height;
        this.width = width;
        this.volume = volume;
        this.price = price;
        this.sessionPriceType = sessionPriceType;
        this.sessionStatus = sessionStatus;
        this.filePath = filePath;
        this.availableEnrollCount = availableEnrollCount;
    }

    public static SessionDto of(SessionDtoBuilder sessionDtoBuilder) {
        return new SessionDto(sessionDtoBuilder.sessionId, sessionDtoBuilder.fileName, sessionDtoBuilder.height, sessionDtoBuilder.width, sessionDtoBuilder.volume, sessionDtoBuilder.price, sessionDtoBuilder.sessionPriceType, sessionDtoBuilder.sessionStatus, sessionDtoBuilder.filePath, sessionDtoBuilder.availableEnrollCount);
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

    public Session toModel() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        return new Session.SessionBuilder()
                .sessionId(this.sessionId)
                .price(this.price)
                .sessionPriceType(this.sessionPriceType)
                .sessionStatus(this.sessionStatus)
                .startDateTime(currentDateTime)
                .endDateTime(currentDateTime.plusMonths(3))
                .availableEnrollCount(this.availableEnrollCount)
                .filePath(this.filePath)
                .build();
    }

    public static class SessionDtoBuilder {
        private Long sessionId;
        private String fileName;
        private int height;
        private int width;
        private int volume;
        private Long price;
        private SessionPriceType sessionPriceType;
        private SessionStatus sessionStatus;
        private String filePath;
        private int availableEnrollCount;

        public SessionDtoBuilder() {
        }

        public SessionDtoBuilder sessionId(Long sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public SessionDtoBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public SessionDtoBuilder height(int height) {
            this.height = height;
            return this;
        }

        public SessionDtoBuilder width(int width) {
            this.width = width;
            return this;
        }

        public SessionDtoBuilder volume(int volume) {
            this.volume = volume;
            return this;
        }

        public SessionDtoBuilder price(Long price) {
            this.price = price;
            return this;
        }

        public SessionDtoBuilder sessionPriceType(SessionPriceType sessionPriceType) {
            this.sessionPriceType = sessionPriceType;
            return this;
        }

        public SessionDtoBuilder sessionStatus(SessionStatus sessionStatus) {
            this.sessionStatus = sessionStatus;
            return this;
        }

        public SessionDtoBuilder filepath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public SessionDtoBuilder availableEnrollCount(int availableEnrollCount) {
            this.availableEnrollCount = availableEnrollCount;
            return this;
        }

        public SessionDto build() {
            return of(this);
        }
    }

}
