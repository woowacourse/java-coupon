package coupon.service;

import coupon.domain.Coupon;
import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
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

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
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
