package coupon.application.coupon;

import coupon.application.coupon.exception.CouponNotFoundException;
import coupon.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponReadService {

    private final CouponRepository couponRepository;

    public CouponResponse findCouponById(Long id) {
        return couponRepository.findById(id)
                .map(CouponResponse::from)
                .orElseThrow(() -> new CouponNotFoundException("없는 쿠폰입니다."));
    }
}
