package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.dto.response.MemberCouponInfo;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import coupon.util.FallbackExecutor;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_ISSUED_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponService couponService;
    private final FallbackExecutor fallbackExecutor;

    @Transactional
    public MemberCoupon issue(long couponId, long memberId) {
        validate(couponId, memberId);
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId, LocalDateTime.now());
        return memberCouponRepository.save(memberCoupon);
    }

    private void validate(long couponId, long memberId) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("존재하지 않는 couponId 입니다.");
        }
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("존재하지 않는 memberId 입니다.");
        }
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        if (memberCoupons.size() >= MAX_ISSUED_COUNT) {
            throw new IllegalArgumentException("회원별 최대 " + MAX_ISSUED_COUNT + "장까지 발급이 가능합니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponInfo> findAllByMemberId(long memberId) {
        return findMemberCouponsByMemberId(memberId)
                .stream()
                .map(this::createMemberCouponInfo)
                .toList();
    }

    private List<MemberCoupon> findMemberCouponsByMemberId(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        if (memberCoupons.isEmpty()) {
            return fallbackExecutor.execute(() -> memberCouponRepository.findAllByMemberId(memberId));
        }
        return memberCoupons;
    }

    private MemberCouponInfo createMemberCouponInfo(MemberCoupon memberCoupon) {
        Coupon coupon = couponService.findById(memberCoupon.getCouponId());
        return MemberCouponInfo.of(memberCoupon, coupon);
    }
}
