package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.dto.CouponAndMemberCouponResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.service.support.DataSourceSupport;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final int ISSUED_MEMBER_COUPON_LIMIT = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final DataSourceSupport dataSourceSupport;

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupon", key = "#couponId")
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElse(getCouponFromWriter(couponId));
    }

    private Coupon getCouponFromWriter(Long couponId) {
        return dataSourceSupport.executeOnWriter(() -> couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 쿠폰 id입니다.")));
    }

    @Transactional
    public MemberCoupon issueMemberCoupon(Long couponId, Member member) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰 id입니다."));
        validateIssueLimit(member, coupon);
        MemberCoupon memberCoupon = MemberCoupon.issue(member, coupon.getId());
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateIssueLimit(Member member, Coupon coupon) {
        List<MemberCoupon> memberCoupon = memberCouponRepository.findAllByMemberAndCouponId(member, coupon.getId());
        if (memberCoupon.size() >= ISSUED_MEMBER_COUPON_LIMIT) {
            throw new IllegalArgumentException("쿠폰을 더 발급할 수 없습니다.");
        }
    }

    @Transactional
    public CouponAndMemberCouponResponse findCouponAndMemberCouponByMember(Member member) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMember(member);
        List<Coupon> coupons = memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .distinct()
                .map(this::getCoupon)
                .toList();
        return new CouponAndMemberCouponResponse(coupons, memberCoupons);
    }
}
