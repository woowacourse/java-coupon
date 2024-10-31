package coupon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member fetchById(long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 정보입니다."));
    }
}
