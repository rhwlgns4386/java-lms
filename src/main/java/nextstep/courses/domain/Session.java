package nextstep.courses.domain;

import java.util.stream.IntStream;

public abstract class Session {

    private final SessionDate date;
    private final SessionImage image;
    private final SessionStatus status;
    private PositiveNumber numOfStudents;

    public Session(SessionDate date, SessionImage image, SessionStatus status, PositiveNumber numOfStudents) {
        this.date = date;
        this.image = image;
        this.status = status;
        this.numOfStudents = numOfStudents;
    }

    public void enroll(int num) {
        IntStream.rangeClosed(0, num)
                .reduce(this.numOfStudents, );
        this.numOfStudents = numOfStudents.increase();
    }
}
