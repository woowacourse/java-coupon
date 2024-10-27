package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponWriteService {

    private final CouponRepository couponRepository;

    @Transactional
    public long create(Coupon coupon) {
        return couponRepository.save(coupon).getId();
    }

    @Transactional
    public Coupon readById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 없습니다. id=" + id));
    }
}
