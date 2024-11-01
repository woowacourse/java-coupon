package coupon.domain;

import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final String name;

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
