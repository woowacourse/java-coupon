package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteCouponService {

    private final CouponRepository couponRepository;

    public void save(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Coupon findById(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 쿠폰입니다."));
    }
}
