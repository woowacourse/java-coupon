package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.repository.MemberCouponRepository;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.util.NewTransactionExecutor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_COUPON_COUNT_PER_MEMBER = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;
    private final NewTransactionExecutor newTransactionExecutor;

    @Transactional
    public MemberCoupon issueCoupon(long memberId, long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);
        validateMemberCouponLimit(memberId, coupon);
        return memberCouponRepository.save(new MemberCoupon(coupon, memberId));
    }

    private void validateMemberCouponLimit(long memberId, Coupon coupon) {
        int issuedCouponCount = memberCouponRepository.countByMemberIdAndCoupon(memberId, coupon);
        if (issuedCouponCount > MAX_COUPON_COUNT_PER_MEMBER) {
            throw new IllegalStateException("1인당 동일한 쿠폰은 5장까지 발급할 수 있습니다. 현재 발급 횟수:%d".formatted(issuedCouponCount));
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getMemberCoupons(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        if (memberCoupons.isEmpty()) {
            log.info("Switching to write DB");
            return newTransactionExecutor.execute(() -> getMemberCouponsFromWriteDB(memberId));
        }
        return memberCoupons.stream()
                .map(this::toMemberCouponResponse)
                .toList();
    }

    public List<MemberCouponResponse> getMemberCouponsFromWriteDB(long memberId) {
        return memberCouponRepository.findByMemberId(memberId)
                .stream()
                .map(this::toMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse toMemberCouponResponse(MemberCoupon memberCoupon) {
        long couponId = memberCoupon.getCoupon().getId();
        Coupon coupon = couponService.getCoupon(couponId);
        return new MemberCouponResponse(memberCoupon.getId(), memberCoupon.getMemberId(), memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(), memberCoupon.getExpireAt(), coupon);
    }
}
