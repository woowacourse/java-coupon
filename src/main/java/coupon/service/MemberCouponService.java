package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponInfo;
import coupon.repository.MemberCouponRepository;
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
    private final CouponService couponService;
    private final FallbackExecutor fallbackExecutor;

    @Transactional
    public MemberCoupon issue(Coupon coupon, Member member) {
        validateMaxCount(member.getId());
        MemberCoupon memberCoupon = new MemberCoupon(coupon.getId(), member.getId(), LocalDateTime.now());
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateMaxCount(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        if (memberCoupons.size() >= MAX_ISSUED_COUNT) {
            throw new IllegalArgumentException("회원별 최대 " + MAX_ISSUED_COUNT + "장까지 발급이 가능합니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponInfo> findAllByMember(Member member) {
        return findMemberCouponsByMemberId(member.getId())
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
