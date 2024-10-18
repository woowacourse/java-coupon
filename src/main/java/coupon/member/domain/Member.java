package coupon.member.domain;

import lombok.Getter;

@Getter
public class Member {

    private final String name;

    public Member(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("회원 이름은 필수입니다.");
        }
        this.name = name;
    }
}
