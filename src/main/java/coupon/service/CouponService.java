package coupon.service;

import coupon.cache.CachedCoupon;
import coupon.domain.Coupon;
import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import coupon.repository.CachedCouponRepository;
import coupon.repository.CouponRepository;
import coupon.util.WriterDbReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final WriterDbReader writerDbReader;
    private final CachedCouponRepository cachedCouponRepository;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    public void update(Coupon coupon) {
        couponRepository.save(coupon);
        cachedCouponRepository.findById(coupon.getId())
                .ifPresent(cachedCoupon -> cachedCouponRepository.save(new CachedCoupon(coupon)));
    }

    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.COUPON_NOT_FOUND));
    }

    public Coupon getCouponInReplicationLag(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponWithWriterDb(id));
    }

    private Coupon getCouponWithWriterDb(Long id) {
        return writerDbReader.read(() -> couponRepository.findById(id))
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.COUPON_NOT_FOUND));
    }
}
