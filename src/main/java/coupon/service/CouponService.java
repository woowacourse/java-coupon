package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void save(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon findById(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 쿠폰입니다."));
    }
}
