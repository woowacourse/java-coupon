package coupon.coupon.service;


import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_UNIT = 500;
    private static final int MIN_ORDER_AMOUNT = 5000;
    private static final int MAX_ORDER_AMOUNT = 100000;

    private final CouponRepository couponRepository;

    @Transactional
    public void create(Coupon coupon) {
        validateDiscountAmount(coupon.getDiscountAmount());
        validateMinOrderAmount(coupon.getMinOrderAmount());
        validateEndDate(coupon.getStartDate(), coupon.getEndDate());
        couponRepository.save(coupon);
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상 10,000원 이하여야 합니다.");
        }
        if (discountAmount % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위여야 합니다.");
        }
    }

    public void validateMinOrderAmount(int minOrderAmount) {
        if (minOrderAmount < MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상이어야 합니다.");
        }
        if (minOrderAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 100,000원 이하여야 합니다.");
        }
    }

    public void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일은 시작일 이후여야 합니다.");
        }
    }


    public Coupon findById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon이 존재하지 않습니다."));
    }
}
