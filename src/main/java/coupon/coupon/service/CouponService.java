package coupon.coupon.service;


import coupon.aspect.WriterTransactional;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.coupon.dto.CouponResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    private final CouponRepository couponRepository;

    @Transactional
    public long create(Coupon coupon) {
        validateDiscountAmount(coupon.getDiscountAmount());
        validateMinOrderAmount(coupon.getMinOrderAmount());
        validateEndDate(coupon.getStartDate(), coupon.getEndDate());
        validateDiscountRate(coupon.calculateDiscountRate());
        Coupon saveCoupon = couponRepository.save(coupon);
        return saveCoupon.getId();
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 %d원 이상 %d원 이하여야 합니다.", MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT)
            );
        }
        if (discountAmount % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액은 %d원 단위여야 합니다.", DISCOUNT_UNIT));
        }
    }

    private void validateMinOrderAmount(int minOrderAmount) {
        if (minOrderAmount < MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format("최소 주문 금액은 %d원 이상이어야 합니다.", MAX_ORDER_AMOUNT));
        }
        if (minOrderAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format("최소 주문 금액은 %d원 이하여야 합니다.", MAX_ORDER_AMOUNT));
        }
    }

    private void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일은 시작일 이후여야 합니다.");
        }
    }

    private void validateDiscountRate(double discountRate) {
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(
                    String.format("할인율은 %d%% 이상 %d%% 이하여야 합니다.", MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE));
        }
    }

    @WriterTransactional
    @Cacheable(value = "coupon", key = "#id")
    public CouponResponse findById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon이 존재하지 않습니다."));
        return CouponResponse.of(coupon);
    }
}
