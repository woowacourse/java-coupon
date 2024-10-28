package coupon.membercoupon.service;

import java.time.LocalDateTime;
import java.util.List;
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

    @Transactional
    public MemberCoupon issue(Member member, Coupon coupon) {
        MemberCoupons memberCoupons = new MemberCoupons(memberCouponRepository.findAllByMemberId(member.getId()));
        memberCoupons.validateCouponIssuanceLimit();
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, LocalDateTime.now());
        return memberCouponRepository.save(memberCoupon);
    }

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
