package coupon;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final int MAX_ISSUABLE_COUPON_COUNTS = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;
    private final LockService lockService;
    private final NewTransactionExecutor<List<MemberCoupon>> newTransactionExecutor;

    @Transactional
    public void issue(Long couponId, Member member) {
        validateIssuedCouponCounts(couponId, member);
        MemberCoupon memberCoupon = new MemberCoupon(couponId, member.getId());
        lockService.executeWithLock(memberCoupon.getId(), () -> memberCouponRepository.save(memberCoupon));
    }

    private void validateIssuedCouponCounts(Long couponId, Member member) {
        Long issuedMemberCouponCount = lockService.executeWithLock(member.getId(), () -> memberCouponRepository.countMemberCouponsByCouponIdAndMemberId(couponId, member.getId()));
        if (MAX_ISSUABLE_COUPON_COUNTS <= issuedMemberCouponCount) {
            throw new IllegalStateException("Issued member coupon counts already exceeded.");
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getMemberCoupons(Long couponId, Long memberId) {
        Coupon coupon = couponService.getCoupon(couponId);
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByCouponAndMember(couponId, memberId);
        if (memberCoupons.isEmpty()) {
            memberCoupons = newTransactionExecutor.execute(() -> memberCouponRepository.findAllByCouponAndMember(couponId, memberId));
        }
        return memberCoupons.stream()
                .map(memberCoupon -> new MemberCouponResponse(coupon, memberCoupon))
                .toList();
    }
}
