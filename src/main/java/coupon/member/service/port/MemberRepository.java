package coupon.member.service.port;

import java.util.Optional;

import coupon.member.domain.Member;

public interface MemberRepository {

    Optional<Member> findById(Long id);
}
