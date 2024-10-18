package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionService transactionService;

    @Transactional
    public Coupon create(final Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(final long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> transactionService.run(() -> couponRepository.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("ID가 %d인 쿠폰이 없습니다.")));
    }
}
