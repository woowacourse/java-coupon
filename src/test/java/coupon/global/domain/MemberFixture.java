package coupon.global.domain;

import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberFixture {

    @Autowired
    private MemberRepository memberRepository;

    public static Member createMember() {
        return new Member(UUID.randomUUID().toString(), "password");
    }
}
