package coupon.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.exception.NotFoundCouponException;
import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import coupon.service.dto.CreateCouponRequest;
import coupon.service.dto.CreateCouponResponse;
import coupon.service.dto.GetCouponResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public CreateCouponResponse createCoupon(final CreateCouponRequest request) {
        Coupon coupon = newCoupon(request);
        final CouponEntity save = couponRepository.save(CouponEntity.toEntity(coupon));
        return CreateCouponResponse.from(save);
    }

    private static Coupon newCoupon(final CreateCouponRequest request) {
        return new Coupon(
                request.couponName(),
                request.discountAmount(),
                request.minimumOrderAmount(),
                request.startDate(),
                request.expirationDate()
        );
    }

    @Transactional(readOnly = true)
    public GetCouponResponse getCoupon(final long id) {
        final CouponEntity couponEntity = couponRepository.findById(id)
                .orElseGet(() -> findCouponEntity(id));
        return GetCouponResponse.from(couponEntity.toDomain());
    }

    @Transactional(propagation = REQUIRES_NEW)
    protected CouponEntity findCouponEntity(final long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new NotFoundCouponException("쿠폰을 찾을 수 없습니다."));
    }
}
