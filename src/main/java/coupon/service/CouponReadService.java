package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponReadService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public Optional<Coupon> readById(Long id) {
        return couponRepository.findById(id);
    }
}
