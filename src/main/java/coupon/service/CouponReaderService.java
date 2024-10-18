package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponReaderService {

    private final CouponRepository couponRepository;

    public CouponEntity getCoupon(final long id) {
        return couponRepository.fetchById(id);
    }
}
