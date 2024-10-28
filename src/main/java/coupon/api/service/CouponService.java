package coupon.api.service;

import coupon.api.repository.CouponRedisRepository;
import coupon.api.repository.CouponRepository;
import coupon.api.repository.MemberCouponRepository;
import coupon.api.repository.MemberRepository;
import coupon.common.exception.CouponNotFoundException;
import coupon.common.exception.MemberNotFoundException;
import coupon.common.response.StorageCouponResponse;
import coupon.domain.coupon.CouponDomain;
import coupon.domain.coupon.UserStorageCoupon;
import coupon.entity.Coupon;
import coupon.entity.Member;
import coupon.entity.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final CouponRedisRepository couponRedisRepository;

    @Transactional
    public Coupon create(CouponDomain couponDomain) {
        Coupon coupon = new Coupon(couponDomain);

        couponRepository.save(coupon);
        couponRedisRepository.addCoupon(coupon);

        return coupon;
    }

    @Transactional
    public void issueCoupon(Long memberId, Long couponId) {
        LocalDateTime issueDate = LocalDateTime.now();

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        MemberCoupon memberCoupon = member.issueCoupon(couponId, issueDate);

        memberCouponRepository.save(memberCoupon);
    }

    @Transactional(readOnly = true)
    public List<StorageCouponResponse> searchAllCoupons(Long memberId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        List<MemberCoupon> memberCoupons = member.getUnusedMemberCoupons();

        return memberCoupons.stream()
                .map(this::makeStorageCoupon)
                .toList();

    }

    @Transactional(readOnly = true)
    public Coupon searchCoupon(Long couponId) {
        return couponRedisRepository.getCoupon(couponId)
                .orElseThrow(CouponNotFoundException::new);
    }

    private StorageCouponResponse makeStorageCoupon(MemberCoupon memberCoupon) {
        UserStorageCoupon coupon = couponRedisRepository.getCoupon(memberCoupon.getCouponId())
                .orElseThrow(CouponNotFoundException::new)
                .toUserStorageCoupon();

        return new StorageCouponResponse(coupon, memberCoupon.getIssuedAt(), memberCoupon.getExpiredAt());
    }
}
