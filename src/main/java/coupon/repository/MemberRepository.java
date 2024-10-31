package coupon.repository;

import coupon.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("멤버 아이디에 해당하는 멤버가 없습니다. id: " + id));
    }
}
