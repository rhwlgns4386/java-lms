package nextstep.users.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.Sessions;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsTeacher;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.TeacherRepository;
import nextstep.users.domain.UserRepository;

@Service
public class UserService {
    @Resource(name = "teacherRepository")
    private TeacherRepository teacherRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Transactional
    public NsTeacher findTeacherWithId(long teacherId) {
        NsTeacher teacher = teacherRepository.findById(teacherId).orElseThrow(NotFoundException::new);

        List<Session> foundSessions = sessionRepository.findByTeacherId(teacherId);
        teacher.addSessions(new Sessions(foundSessions));
        return teacher;
    }

    @Transactional
    public List<NsUser> findAllStudentsByIds(List<Long> ids) {
        List<NsUser> students = new ArrayList<>();
        for (Long id : ids) {
            students.add(userRepository.findById(id).orElseThrow(NotFoundException::new));
        }
        return students;
    }
}
