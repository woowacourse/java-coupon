package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.domain.repository.CouponRepository;
import coupon.domain.repository.MemberCouponRepository;
import coupon.service.dto.MemberCouponDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON_COUNT = 5;

    private MemberCouponRepository memberCouponRepository;
    private CouponRepository couponRepository;
    private CacheManager cacheManager;

    public void create(MemberCoupon memberCoupon) {
        validateMemberIssueLimit(memberCoupon);
        memberCouponRepository.save(memberCoupon);
    }

    private void validateMemberIssueLimit(MemberCoupon memberCoupon) {
        Long memberId = memberCoupon.getMemberId();
        Long couponId = memberCoupon.getCouponId();

        long memberCouponCount = memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);

        if (memberCouponCount >= MAX_MEMBER_COUPON_COUNT) {
            throw new IllegalArgumentException("한 명의 회원은 동일한 쿠폰을 최대 5장까지 발급할 수 있습니다.");
        }
    }
}
