package coupon.service;

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

    private static final int MAX_READ_ATTEMPT = 10;

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
        int attempts = 0;
        while (attempts < MAX_READ_ATTEMPT) {
            try {
                final CouponEntity couponEntity = couponRepository.findById(id)
                        .orElseThrow(() -> new NotFoundCouponException("존재하지 않는 쿠폰입니다."));
                return GetCouponResponse.from(couponEntity.toDomain());
            } catch (NotFoundCouponException e) {
                attempts++;
                sleep();
            }
        }
        throw new NotFoundCouponException("쿠폰을 찾을 수 없습니다.");
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("쿠폰 조회 대기 중 예외가 발생했습니다.", ie);
        }
    }
}
