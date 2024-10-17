package coupon.service;

import coupon.domain.Coupon;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponQueryService {

    private final CouponRepository couponRepository;

    public Coupon findById(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponException("쿠폰이 존재하지 않습니다."));
    }
}
