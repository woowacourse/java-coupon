package coupon.service;

import coupon.domain.Coupon;
import coupon.dto.SaveCouponRequest;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponCommandService {

    private final CouponRepository couponRepository;

    public long save(SaveCouponRequest request) {
        Coupon coupon = couponRepository.save(new Coupon(
                request.name(),
                request.discountMoney(),
                request.minimumOrderMoney(),
                request.sinceDate(),
                request.untilDate(),
                request.category()));

        return coupon.getId();
    }
}
