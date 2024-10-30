package nextstep.office;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.Student;
import nextstep.payments.domain.Payment;

import java.util.ArrayList;
import java.util.List;

public class Office {
    private String name;
    private String address;
    private List<Course> courses = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();

    public Office(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void registerCourse(Course course) {
        courses.add(course);
    }

    public void addSessionToCourse(Course course, Session session) {
        course.addSession(session);
    }

    public void processPayment(Student student, Long amount, Course course, Session session) {
        payments.add(course.processPayment(student, amount, session));
    }

}
