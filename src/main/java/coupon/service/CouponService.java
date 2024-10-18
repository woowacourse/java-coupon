package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Long create(CouponRequest request) {
        Coupon coupon = new Coupon(request.name(), request.minimumOrderAmount(), request.discountAmount(),
                Category.valueOf(request.category()),
                request.issueStartDate(), request.issueEndDate());
        return couponRepository.save(coupon).getId();
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + id + ")의 쿠폰을 찾을 수 없습니다."));
    }
}
