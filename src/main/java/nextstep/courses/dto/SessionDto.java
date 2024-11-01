package nextstep.courses.dto;

import java.time.LocalDateTime;

public class SessionDto {
    private Long courseId;
    private String payType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long sessionPay;
    private Integer maximumNumberPeople;
    private MultipartFile multipartFile;

    public SessionDto(Long courseId, String payType, LocalDateTime startDate, LocalDateTime endDate, Integer maximumNumberPeople, Long sessionPay) {
        this.payType = payType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maximumNumberPeople = maximumNumberPeople;
        this.sessionPay = sessionPay;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getPayType() {
        return payType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getSessionPay() {
        return sessionPay;
    }

    public Integer getMaximumNumberPeople() {
        return maximumNumberPeople;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setMaximumNumberPeople(Integer maximumNumberPeople) {
        this.maximumNumberPeople = maximumNumberPeople;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
