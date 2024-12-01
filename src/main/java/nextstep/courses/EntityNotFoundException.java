package nextstep.courses;

public class EntityNotFoundException extends IllegalArgumentException {
    public EntityNotFoundException(Class<?> tClass) {
        super(String.format("%s를 찾을 수 없습니다", tClass.getSimpleName()));
    }
}
