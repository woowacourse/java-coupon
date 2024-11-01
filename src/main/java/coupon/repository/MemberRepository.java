package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coupon.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
