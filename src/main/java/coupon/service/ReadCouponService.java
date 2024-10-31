package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadCouponService {

    private final WriteCouponService writeCouponService;
    private final CouponRepository couponRepository;

    @Cacheable(key = "#id", value = "coupons")
    public Coupon findById(long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> writeCouponService.findById(id));
    }
}
