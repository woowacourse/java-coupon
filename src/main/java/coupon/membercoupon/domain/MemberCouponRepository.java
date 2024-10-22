package coupon.membercoupon.domain;

import java.util.List;
import org.springframework.data.repository.Repository;

public interface MemberCouponRepository extends Repository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberId(Long memberId);
}
