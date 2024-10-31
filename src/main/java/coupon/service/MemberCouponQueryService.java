package coupon.service;

import coupon.domain.coupon.MemberCoupon;
import coupon.dto.response.GetMemberCouponResponse;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberCouponQueryService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    public MemberCouponQueryService(
            MemberCouponRepository memberCouponRepository,
            CouponService couponService
    ) {
        this.memberCouponRepository = memberCouponRepository;
        this.couponService = couponService;
    }

    public List<GetMemberCouponResponse> getMemberCoupons(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);

        return memberCoupons.stream()
                .map(memberCoupon -> new GetMemberCouponResponse(
                        memberCoupon,
                        couponService.getCoupon(memberCoupon.getCouponId())
                ))
                .toList();
    }
}
