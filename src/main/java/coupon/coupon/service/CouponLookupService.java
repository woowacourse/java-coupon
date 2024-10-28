package coupon.coupon.service;

import coupon.coupon.repository.CouponRepository;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CouponLookupService {

    private final CouponRepository couponRepository;
    private final Clock clock;

    public List<CouponResponse> getAllIssuableCoupons() {
        LocalDate currentDate = LocalDate.now(clock);
        return couponRepository.findAllIssuableCoupons(currentDate)
                .stream()
                .map(CouponResponse::from)
                .toList();
    }
}
