package coupon.service;

import coupon.config.DataSourceRouter;
import coupon.domain.Coupon;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class CouponQueryFallbackService {

    private final CouponRepository couponRepository;

    public Coupon findById(long id) {
        DataSourceRouter.enableWriterDatabase();
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponException("쿠폰이 존재하지 않습니다."));
        DataSourceRouter.disableWriterDatabase();
        return coupon;
    }
}
