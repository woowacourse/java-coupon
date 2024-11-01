package coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MEMBER_COUPON_COUNT_MAX = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    @Transactional
    public MemberCoupon issueMemberCoupon(Member member, Coupon coupon) {
        int memberCouponCount = memberCouponRepository.countByMemberIdAndCouponId(member.getId(), coupon.getId());
        if (memberCouponCount >= MEMBER_COUPON_COUNT_MAX) {
            throw new IllegalArgumentException(
                    String.format("동일한 쿠폰은 %d장까지만 발급받을 수 있습니다.", MEMBER_COUPON_COUNT_MAX));
        }
        return memberCouponRepository.save(new MemberCoupon(member, coupon));
    }

    @Transactional
    public List<Coupon> findAllCouponByMember(Member member) {
        return memberCouponRepository.findAllByMemberId(member.getId()).stream()
                .map(MemberCoupon::getCouponId)
                .map(couponService::getCoupon)
                .toList();
    }
}
