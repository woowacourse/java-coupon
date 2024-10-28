package coupon.api.repository;

import coupon.entity.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {

    Optional<Member> findMemberById(Long memberId);

    void save(Member member);
}
