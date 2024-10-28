package coupon.member.repository;

import coupon.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }
}
