package coupon.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.exception.DuplicateCouponException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.entity.MemberCouponEntity;
import coupon.service.dto.CouponInfoResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    public static final int MAX_DUPLICATE_COUPON = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public MemberCouponEntity provideCoupon(final MemberCoupon memberCoupon) {
        final long couponCount = memberCouponRepository.countByMemberIdAndCouponId(
                memberCoupon.getMemberId(),
                memberCoupon.getCouponId()
        );
        if (couponCount >= MAX_DUPLICATE_COUPON) {
            throw new DuplicateCouponException("동일한 쿠폰은 5개를 초과하여 발급할 수 없습니다.");
        }
        return memberCouponRepository.save(MemberCouponEntity.toEntity(memberCoupon));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "member-coupon", key = "#p0")
    public List<CouponInfoResponse> getCouponsByMember(final long memberId) {
        final List<MemberCouponEntity> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        return memberCoupons.stream()
                .map(memberCoupon -> {
                    final Coupon coupon = couponRepository.findById(memberCoupon.getCouponId())
                            .orElseThrow(() -> new RuntimeException("쿠폰을 찾을 수 없습니다."))
                            .toDomain();
                    return CouponInfoResponse.of(memberCoupon.toDomain(), coupon);
                })
                .toList();
    }
}
