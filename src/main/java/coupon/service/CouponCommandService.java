package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.SaveCouponRequest;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponCommandService {

    private static final int MAX_COUPON_ISSUE_COUNT = 5;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    public long save(SaveCouponRequest request) {
        Coupon coupon = couponRepository.save(new Coupon(
                request.name(),
                request.discountMoney(),
                request.minimumOrderMoney(),
                request.sinceDate(),
                request.untilDate(),
                request.category()));

        return coupon.getId();
    }

    public void issue(Member member, Coupon coupon) {
        if (memberCouponRepository.countByMemberAndCoupon(member, coupon) >= MAX_COUPON_ISSUE_COUNT) {
            throw new CouponException("동일한 쿠폰은 최대 %d장까지 발행할 수 있습니다.".formatted(MAX_COUPON_ISSUE_COUNT));
        }

        memberCouponRepository.save(new MemberCoupon(member, coupon));
    }
}
