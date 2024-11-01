package coupon.membercoupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponEntity;
import coupon.coupon.service.CouponReader;
import coupon.member.repository.MemberEntity;
import coupon.membercoupon.domain.AvailablePeriod;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.repository.MemberCouponEntity;
import coupon.membercoupon.repository.MemberCouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class MemberCouponWriter {

    private static int MAX_AVAILABLE_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponReader couponReader;

    @Transactional
    public Long issueCoupon(MemberEntity memberEntity, Long couponId) {
        validateCanIssuable(memberEntity);
        CouponEntity couponEntity = couponReader.findById(couponId);
        Coupon coupon = couponEntity.toDomain();
        AvailablePeriod availablePeriod = new AvailablePeriod(coupon.getIssuedPeriod());
        MemberCoupon memberCoupon = new MemberCoupon(true, availablePeriod);
        MemberCouponEntity newMemberCoupon = new MemberCouponEntity(couponId, memberEntity.getId(), memberCoupon);
        MemberCouponEntity savedMemberCoupon = memberCouponRepository.save(newMemberCoupon);
        return savedMemberCoupon.getCouponId();
    }

    private void validateCanIssuable(MemberEntity memberEntity) {
        int issuedCount = memberCouponRepository.findAllByMemberId(memberEntity.getId()).size();
        if (issuedCount >= MAX_AVAILABLE_COUNT) {
            throw new RuntimeException("쿠폰의 최대 발급 개수를 넘었습니다. [%d]".formatted(MAX_AVAILABLE_COUNT));
        }
    }
}
