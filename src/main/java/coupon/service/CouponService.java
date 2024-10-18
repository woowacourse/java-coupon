package coupon.service;

import org.springframework.stereotype.Service;

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

    public GetCouponResponse getCoupon(final long id) {
        final CouponEntity couponEntity = couponRepository.findById(id)
                .orElseThrow(() -> new NotFoundCouponException("존재하지 않는 쿠폰입니다."));
        return GetCouponResponse.from(couponEntity.toDomain());
    }
}
