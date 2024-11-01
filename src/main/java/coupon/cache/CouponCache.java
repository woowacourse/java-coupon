package coupon.cache;

import coupon.domain.EmptyMemberCouponDetails;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponDetails;
import coupon.entity.cache.CachedCouponEntity;
import coupon.repository.cache.CachedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponCache {

    private final CachedCouponRepository cachedCouponRepository;

    public MemberCouponDetails hitCache(MemberCoupon memberCoupon) {
        Optional<CachedCouponEntity> optionalCachedCouponEntity = cachedCouponRepository.findById(memberCoupon.getCouponId());
        if (optionalCachedCouponEntity.isEmpty()) {
            return new EmptyMemberCouponDetails();
        }
        return new MemberCouponDetails(memberCoupon, optionalCachedCouponEntity.get().toCoupon());
    }

    public void write(MemberCouponDetails memberCouponDetails) {
        if (memberCouponDetails.isNotEmpty()) {
            cachedCouponRepository.save(CachedCouponEntity.from(memberCouponDetails.getCoupon()));
        }
    }
}
