package coupon.membercoupon;

import coupon.coupon.Coupon;
import coupon.coupon.CouponIssuanceDatePassedException;
import coupon.coupon.CouponNotFoundException;
import coupon.coupon.CouponRepository;
import coupon.member.Member;
import coupon.member.MemberNotFoundException;
import coupon.member.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    @CacheEvict(value = "memberCoupon", key = "#memberId")
    public MemberCoupon issueMemberCoupon(long couponId, long memberId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        validateIssuedMemberCouponCount(member.getId());
        validateCouponIssuable(coupon);
        return memberCouponRepository.save(new MemberCoupon(coupon.getId(), member.getId()));
    }

    private void validateIssuedMemberCouponCount(long memberId) {
        int issuedCount = memberCouponRepository.findAllByMemberId(memberId).size();
        if (issuedCount >= 5) {
            throw new MemberCouponIssuedOverException(memberId);
        }
    }

    private void validateCouponIssuable(Coupon coupon) {
        if (!coupon.isCouponIssuable()) {
            throw new CouponIssuanceDatePassedException(coupon.getId());
        }
    }

    @Transactional
    @Cacheable(value = "memberCoupon", key = "#memberId")
    public MemberCouponResponse findMemberCoupons(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(member.getId());
        List<CouponResponse> couponResponses = memberCoupons.stream()
                .map(this::toCouponResponse)
                .toList();

        return new MemberCouponResponse(member.getName(), couponResponses);
    }

    private CouponResponse toCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponRepository.findById(memberCoupon.getCouponId())
                .orElseThrow(RuntimeException::new);

        return new CouponResponse(
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinOrderAmount(),
                coupon.getCategory().toString(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt().toLocalDate(),
                memberCoupon.getExpiredAt().toLocalDate()
        );
    }
}
