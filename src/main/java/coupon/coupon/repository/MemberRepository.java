package coupon.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import coupon.coupon.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
