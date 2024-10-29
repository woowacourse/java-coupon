package coupon.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
