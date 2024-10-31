package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import coupon.util.TransactionWriterExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionWriterExecutor transactionWriterExecutor;


    public long create(Coupon coupon) {
        return couponRepository.save(coupon).getId();
    }

    @Cacheable(key = "#id", value = "coupon")
    public Coupon read(Long id) {
        return couponRepository.findById(id).
                orElse(findById(id));
    }

    private Coupon findById(Long id) {
        return transactionWriterExecutor.execute(
                () -> couponRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("쿠폰이 없습니다. id=" + id))
        );
    }
}
