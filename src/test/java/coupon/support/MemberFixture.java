package coupon.support;

import coupon.domain.Member;

public class MemberFixture {

    public static Member create(String name) {
        return new Member(name);
    }
}
