package coupon.service.coupon;

import coupon.entity.coupon.Coupon;
import coupon.exception.coupon.CouponNotFoundException;
import coupon.helper.CacheExecutor;
import coupon.helper.TransactionExecutor;
import coupon.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionExecutor transactionExecutor;
    private final CacheExecutor cacheExecutor;

    @Transactional
    public Long create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        return savedCoupon.getId();
    }

    @Transactional(readOnly = true)
    public Coupon getCouponWithReplicaLag(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));
    }

    /* coupon 조회 로직 (복제지연 고려)
     * 1. 읽기 db에서 값을 읽는다
     * 2. 읽기 db에 값이 없으면 쓰기 db에서 읽는다
     * */
    @Transactional(readOnly = true)
    public Coupon getCouponWithoutReplicaLag(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponFromWriterDatabase(id));
    }

    private Coupon getCouponFromWriterDatabase(Long id) {
        return transactionExecutor.executeNewTransaction(() ->
                couponRepository.findById(id)
                        .orElseThrow(() -> new CouponNotFoundException(id))
        );
    }

    /* coupon 조회 로직 (캐시로 성능 개선, 복제 지연 고려)
     * 1. 캐시에서 값을 읽는다
     * 2. 캐시에 값이 없다면 읽기 db에서 값을 읽는다
     * 3. 읽기 db에 값이 없으면 쓰기 db에서 읽는다
     * 4. 읽기 db든 쓰기 db든 캐시에 값을 저장한다
     * */
    public Coupon getCouponFromCache(Long id) {
        return cacheExecutor.executeWithCache(id, () ->
                couponRepository.findById(id)
                        .orElseGet(() -> getCouponFromWriterDatabase(id))
        );
    }
}
