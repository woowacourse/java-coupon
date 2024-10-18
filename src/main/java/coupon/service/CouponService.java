package coupon.service;

import coupon.domain.Coupon;
import coupon.dto.request.CouponSaveRequest;
import coupon.repository.CouponRepository;
import coupon.util.WriteDBConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final WriteDBConnector writeDBConnector;

    @Transactional
    public Coupon save(CouponSaveRequest couponSaveRequest) {
        Coupon coupon = couponSaveRequest.toCoupon();
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon findById(long couponId) {
        return couponRepository.findById(couponId)
                .orElse(findByIdWithWriteDB(couponId));
    }

    private Coupon findByIdWithWriteDB(long couponId) {
        return writeDBConnector.apply(countId -> couponRepository.findById(couponId), couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }
}
