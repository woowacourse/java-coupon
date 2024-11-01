package coupon.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.repository.MemberCouponRepository;

@Service
@Transactional(readOnly = true)
public class MemberCouponService {

    private static final int COUPON_ISSUABLE_LIMIT = 5;

    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponService(CouponService couponService, MemberCouponRepository memberCouponRepository) {
        this.couponService = couponService;
        this.memberCouponRepository = memberCouponRepository;
    }

    @Transactional
    public long create(Member member, long couponId, LocalDate issuedAt) {
        Coupon coupon = couponService.getCoupon(couponId);
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberAndCoupon(member, coupon);
        validateIssuableLimit(memberCoupons);

        MemberCoupon memberCoupon = new MemberCoupon(member, coupon, issuedAt);
        memberCouponRepository.save(memberCoupon);

        return memberCoupon.getId();
    }

    private void validateIssuableLimit(List<MemberCoupon> memberCoupons) {
        if (memberCoupons.size() >= COUPON_ISSUABLE_LIMIT) {
            throw new IllegalArgumentException(String.format("가능한 수량인 %d개를 모두 발급하셨기에 추가 발급이 불가능합니다.", COUPON_ISSUABLE_LIMIT));
        }
    }

    @Transactional
    public Optional<MemberCoupon> findById(long memberCouponId) {
        return memberCouponRepository.findById(memberCouponId);
    }

    @Transactional
    public List<MemberCoupon> findAllByMemberAndCoupon(Member member, Coupon coupon) {
        return memberCouponRepository.findAllByMemberAndCoupon(member, coupon);
    }

    @Transactional
    public List<MemberCoupon> getMemberCoupons(Member member) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMember(member);

        // 영속성 컨텍스트에 Coupon들을 올리는 작업 -> 하나의 쿼리로 가능한가?
        memberCoupons.stream()
                .map(memberCoupon -> memberCoupon.getCoupon().getId())
                .forEach(couponId -> couponService.getCoupon(couponId));

        memberCoupons.forEach(memberCoupon -> memberCoupon.getCoupon().getName());
        return memberCoupons;
    }

    @Transactional
    public void deleteAll() {
        memberCouponRepository.deleteAllInBatch();
    }
}
