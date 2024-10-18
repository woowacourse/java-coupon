package coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import coupon.domain.Coupon;
import coupon.repository.CouponEntity;
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
        return CreateCouponResponse.from(save.toDomain());
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

    public List<GetCouponResponse> getAllCoupon() {
        final List<Coupon> coupons = couponRepository.findAll()
                .stream()
                .map(CouponEntity::toDomain)
                .toList();
        return GetCouponResponse.from(coupons);
    }
}
