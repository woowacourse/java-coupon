package coupon.domain.member.repository;

import coupon.domain.member.Member;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long memberId);
}
