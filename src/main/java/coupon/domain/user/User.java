package coupon.domain.user;

import lombok.Getter;

@Getter
public class User {

    private Long id;
    private final String name;

    public User(String name) {
        this.name = name;
    }
}
