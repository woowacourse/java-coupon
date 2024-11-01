package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
