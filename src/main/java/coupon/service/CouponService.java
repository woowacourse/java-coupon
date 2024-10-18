package coupon.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.service.dto.request.CouponCreateRequest;
import coupon.service.port.CouponRepository;

@Transactional(readOnly = true)
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(final CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public void createCoupon(final CouponCreateRequest request) {
        final Coupon createdCoupon = Coupon.create(
                request.name(),
                request.minimumOrderAmount(),
                request.discountAmount(),
                request.productionCategory(),
                request.couponStartDate(),
                request.couponEndDate()
        );
        couponRepository.save(createdCoupon);
    }

    public Coupon findByName(final String name) {
        final CouponName couponName = new CouponName(name);
        return couponRepository.findByName(couponName)
                .orElseThrow(() -> new NoSuchElementException("입력된 쿠폰 이름에 일치하는 쿠폰 정보가 존재하지 않습니다. -" + name));
    }
}
