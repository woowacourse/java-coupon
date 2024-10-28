package coupon.membercoupon.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import coupon.coupons.domain.Coupon;
import coupon.coupons.domain.Coupons;
import coupon.coupons.service.CouponService;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponDetail;
import coupon.membercoupon.domain.MemberCoupons;
import coupon.membercoupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    @CachePut(value = "member_coupons", key = "#member.id")
    @Transactional
    public List<MemberCouponDetail> issueCoupons(Member member, List<Coupon> coupons) {
        MemberCoupons memberCoupons = new MemberCoupons(memberCouponRepository.findAllByMemberId(member.getId()));
        memberCoupons.validateCouponIssuanceLimit(coupons.size());
        for (Coupon coupon : coupons) {
            MemberCoupon memberCoupon = new MemberCoupon(coupon, member, LocalDateTime.now());
            MemberCoupon saved = memberCouponRepository.save(memberCoupon);
            memberCoupons.add(saved);
        }
        return memberCoupons.getMemberCouponDetails(new Coupons(coupons));
    }

    @Cacheable(value = "member_coupons", key = "#member.id")
    public List<MemberCouponDetail> findAllBy(Member member) {
        MemberCoupons memberCoupons = new MemberCoupons(memberCouponRepository.findAllByMemberId(member.getId()));
        List<Long> couponsIds = memberCoupons.getCouponsIds();
        Coupons coupons = new Coupons(getCoupons(couponsIds));
        return memberCoupons.getMemberCouponDetails(coupons);
    }

    private List<Coupon> getCoupons(List<Long> couponsIds) {
        return couponsIds.stream()
                .map(couponService::getCoupon)
                .toList();
    }
}
