package coupon.coupon.service;

import coupon.coupon.entity.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponReaderService {

    private final CouponRepository couponRepository;

    public Optional<CouponEntity> getCoupon(long couponId) {
        return couponRepository.findById(couponId);
    }
}
