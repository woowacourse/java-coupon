package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.repository.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
