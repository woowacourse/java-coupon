package coupon.domain.member.repository;

import coupon.domain.member.Member;
import coupon.exception.MemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getById(long id) {
        return findById(id)
                .orElseThrow(() -> new MemberNotFoundException("조회하신 회원 정보가 존재하지 않습니다. (memberId: %d)"
                        .formatted(id)));
    }
}
