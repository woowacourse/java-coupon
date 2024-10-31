package coupon.coupon.service;

import coupon.coupon.repository.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponLookupService {

    private final CouponRepository couponRepository;
    private final Clock clock;

    public List<CouponEntity> getAllIssuableCoupons() {
        LocalDate currentDate = LocalDate.now(clock);
        return couponRepository.findAllIssuableCoupons(currentDate)
                .stream()
                .toList();
    }

    @Cacheable(value = "coupon", key = "#id")
    public CouponEntity getCoupon(long id) {
        return couponRepository.findByIdOrThrow(id);
    }
}
