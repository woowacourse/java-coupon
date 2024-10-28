package coupon.membercoupon;

import coupon.coupon.Coupon;
import coupon.coupon.CouponRepository;
import coupon.member.Member;
import coupon.member.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @CacheEvict(value = "memberCoupon", key = "#memberId")
    public void issueMemberCoupon(long couponId, long memberId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 쿠폰 정보가 없습니다. couponId : " + couponId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보가 없습니다. memberId : " + memberId));
        validateIssuedMemberCouponCount(member.getId());
        validateCouponIssuable(coupon);
        memberCouponRepository.save(new MemberCoupon(coupon.getId(), member.getId()));
    }

    private void validateIssuedMemberCouponCount(long memberId) {
        int issuedCount = memberCouponRepository.findAllByMemberId(memberId).size();
        if (issuedCount > 5) {
            throw new IllegalArgumentException("쿠폰은 최대 5장까지 발급할 수 있습니다. memberId : " + memberId);
        }
    }

    private void validateCouponIssuable(Coupon coupon) {
        if (!coupon.isCouponIssuable()) {
            throw new IllegalArgumentException("쿠폰의 발급 가능일이 지났습니다. couponEndDate : " + coupon.getEndDate());
        }
    }

    @Cacheable(value = "memberCoupon", key = "#memberId")
    public MemberCouponResponse findMemberCoupons(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보가 없습니다. memberId : " + memberId));

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
