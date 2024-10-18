package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponQueryService {

    private final CouponQueryFallbackService couponQueryFallbackService;
    private final CouponRepository couponRepository;

    public Coupon findById(long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> couponQueryFallbackService.findById(id));
    }
}
