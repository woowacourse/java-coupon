package coupon.service;

import coupon.domain.Coupon;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponQueryService {

    private final CouponRepository couponRepository;

    @Cacheable(value = "coupon", key = "#id")
    public Coupon findById(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponException("쿠폰이 존재하지 않습니다."));
    }

    @Cacheable(value = "coupons", key = "#memberId")
    public List<Coupon> findMine(long memberId) {
        return couponRepository.findMine(memberId);
    }
}
