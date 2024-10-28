package coupon.service;

import coupon.dto.MemberCouponResponse;
import coupon.entity.Member;
import coupon.entity.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import coupon.validator.CouponValidator;
import coupon.validator.MemberCouponValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberCouponValidator memberCouponValidator;
    private final CouponValidator couponValidator;

    @Transactional
    public MemberCoupon create(Member member, long couponId) {
        memberCouponValidator.validateIssuedCount(member);
        couponValidator.validateCanIssue(couponService.getCoupon(couponId));

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .couponId(couponId)
                .member(member)
                .build();

        return memberCouponRepository.save(memberCoupon);
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getMemberCoupons(Member member) {
        return memberCouponRepository.findAllByMemberId(member.getId()).stream()
                .map(memberCoupon -> MemberCouponResponse.from(
                        memberCoupon, couponService.getCoupon(memberCoupon.getCouponId())
                ))
                .toList();
    }
}
