package coupon.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
