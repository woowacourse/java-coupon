package coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.member.MemberCoupon;
import coupon.repository.CouponCache;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.service.dto.MemberCouponResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int COUPON_LIMIT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final CouponService couponService;

    @Transactional
    public void create(Member member, long couponId) {
        validate(member, couponId);
        MemberCoupon memberCoupon = new MemberCoupon(couponId, member.getId());
        memberCouponRepository.save(memberCoupon);
    }

    private void validate(Member member, long couponId) {
        if (memberCouponRepository.countByMemberIdAndCouponId(member.getId(), couponId) >= COUPON_LIMIT) {
            throw new IllegalArgumentException(String.format("발급 가능한 수량은 최대 %d 개 입니다", COUPON_LIMIT));
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> readAll(long id) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(id);
        return memberCoupons.stream()
                .map(memberCoupon -> new MemberCouponResponse(
                        memberCoupon,
                        getCoupon(memberCoupon.getCouponId()))
                )
                .toList();
    }

    private Coupon getCoupon(long couponId) {
        Coupon coupon = CouponCache.getCoupon(couponId);
        if (coupon == null) {
            coupon = couponService.getCoupon(couponId);
        }
        return coupon;
    }
}
