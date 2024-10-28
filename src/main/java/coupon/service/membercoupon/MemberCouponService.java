package coupon.service.membercoupon;

import coupon.entity.coupon.Coupon;
import coupon.entity.membercoupon.MemberCoupon;
import coupon.exception.membercoupon.CannotCreateMemberCouponException;
import coupon.repository.membercoupon.MemberCouponRepository;
import coupon.service.coupon.CouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON_CREATE_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;


    @Transactional
    public Long create(MemberCoupon memberCoupon) {
        validate(memberCoupon);
        MemberCoupon savedMemberCoupon = memberCouponRepository.save(memberCoupon);
        return savedMemberCoupon.getId();
    }

    private void validate(MemberCoupon memberCoupon) {
        if (memberCouponRepository.countByMemberIdAndCouponId(memberCoupon.getMemberId(), memberCoupon.getCouponId())
                == MAX_MEMBER_COUPON_CREATE_COUNT) {
            throw new CannotCreateMemberCouponException();
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getMemberCoupons(Long memberId) {
        return memberCouponRepository.findByMemberId(memberId).stream()
                .map(this::toMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse toMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponService.getCouponFromCache(memberCoupon.getCouponId());
        return new MemberCouponResponse(memberCoupon, coupon);
    }
}
