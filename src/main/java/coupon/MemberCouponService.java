package coupon;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;
    private final RedisCacheService redisCacheService;
    private final LockService lockService;

    @Transactional
    public void issue(Long couponId, Member member) {
        // 회원과 쿠폰 번호로 사용한거 안 한거 합쳐서 5개 미만이면, 4개 이하면 발급.
        Long issuedMemberCouponCount = lockService.executeWithLock(member.getId(), () -> memberCouponRepository.countMemberCouponsByCouponIdAndMemberId(couponId, member.getId()));
        if (issuedMemberCouponCount < 5) {
            MemberCoupon memberCoupon = new MemberCoupon(couponId, member.getId());
            lockService.executeWithLock(memberCoupon.getId(), () -> memberCouponRepository.save(memberCoupon));
            return;
        }
        throw new IllegalStateException("Issued member coupon counts already exceeded.");
    }

    @Transactional(readOnly = true)
    public List<MemberCoupon> getMemberCoupons(Member member) {
//        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(member.getId());
//        memberCoupons.stream()
//                .map(mc -> {
//                    Coupon coupon = couponService.getCoupon(mc.getCouponId());
//                    // 쿠폰과 맴버 쿠폰을 합쳐서 DTO 생성 후 반환.
//                    // 메서드 매개변수 DTO로 변환해야됨.
//                });
        return null;
    }
}
