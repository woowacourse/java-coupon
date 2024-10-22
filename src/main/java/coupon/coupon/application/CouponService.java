package coupon.coupon.application;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Transactional(readOnly = true)
    public List<CouponResponse> getCoupons() {
        List<Coupon> coupons = couponRepository.findAll();

        return couponMapper.toResponses(coupons);
    }

    @Transactional(readOnly = true)
    public CouponResponse getCoupon(Long couponId) {
        String message = "존재하지 않는 쿠폰입니다. id: %d".formatted(couponId);

        return couponRepository.findById(couponId)
                .map(couponMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException(message));
    }

    @Transactional(readOnly = true)
    public Map<Long, Coupon> getCouponMapEachById(List<Long> couponIds) {
        List<Coupon> coupons = couponRepository.findAllByIdIn(couponIds);

        return coupons.stream()
                .collect(Collectors.toMap(Coupon::getId, Function.identity()));
    }

    @Transactional
    public Long createCoupon(CreateCouponRequest request) {
        Coupon coupon = couponMapper.toCoupon(request);

        couponRepository.save(coupon);

        return coupon.getId();
    }
}
