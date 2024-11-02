package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionFactory {

    public static Session createSession(String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate,
                                        long salePrice, StateCode stateCode, int createId,
                                        int fileSize, String type, int width, int height, String fileName, boolean isPaid) {
        return createSession(title, applyStartDate, applyEndDate, salePrice, stateCode, createId, fileSize, type, width, height, fileName, 0, isPaid);
    }

    //유료랑 무료 필드가 들어왔을때 계속해서 추가해야하나?
    public static Session createSession(String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate,
                                        long salePrice, StateCode stateCode, int createId,
                                        int fileSize, String type, int width, int height, String fileName, int studentMaxCount, boolean isPaid) {
        if (isPaid) {
            return new PaidSession(title, applyStartDate, applyEndDate, salePrice, stateCode, createId, fileSize, type, width, height, fileName, studentMaxCount);
        }

        return new FreeSession(title, applyStartDate, applyEndDate, salePrice, stateCode, createId, fileSize, type, width, height, fileName);
    }
}

