package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.DiscountPolicy;
import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final List<DiscountPolicy> discountPolicies;

    @Transactional
    public CouponEntity create(CouponCreationRequest request) {
        Coupon coupon = new Coupon(
                request.name(), request.category(),
                request.discountPrice(), request.minimumOrderPrice(), discountPolicies,
                request.issuableDate(), request.expirationDate()
        );
        return couponRepository.save(CouponEntity.from(coupon));
    }

    @Transactional(readOnly = true)
    public CouponEntity getCoupon(long id) {
        return couponRepository.findByIdOrThrow(id);
    }
}
