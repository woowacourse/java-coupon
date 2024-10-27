package coupon.repository;

import coupon.domain.Coupon;
import coupon.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponReader {
    private final CouponRepository couponRepository;
    private final SessionUtil sessionUtil;

    public Coupon findById(final UUID uuid) {
        sessionUtil.logSessionStatus("findById");
        return couponRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s 에 해당하는 쿠폰이 없습니다.", uuid)));
    }
}
