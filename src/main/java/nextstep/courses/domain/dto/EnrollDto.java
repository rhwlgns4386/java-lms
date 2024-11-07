package nextstep.courses.domain.dto;

import nextstep.users.domain.NsUser;

public class EnrollDto {

    private final Long id;
    private final NsUser nsUser;
    private final Long price;

    public EnrollDto(Long id, NsUser nsUser, Long price) {
        this.id = id;
        this.nsUser = nsUser;
        this.price = price;
    }

    public NsUser getNsUser() {
        return nsUser;
    }

    public Long getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

}
