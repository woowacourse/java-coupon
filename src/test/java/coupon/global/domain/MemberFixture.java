package coupon.global.domain;

import coupon.member.domain.Member;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MemberFixture {

    public static Member createMember() {
        return new Member(UUID.randomUUID().toString(), "password");
    }
}
