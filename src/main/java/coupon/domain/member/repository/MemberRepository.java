package coupon.domain.member.repository;

import coupon.domain.member.Member;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(Long memberId);
}
