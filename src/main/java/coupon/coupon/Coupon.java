package coupon.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;

    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int UNIT_DISCOUNT_AMOUNT = 500;

    private static final int MIN_OF_MIN_ORDER_AMOUNT = 5_000;
    private static final int MAX_OF_MIN_ORDER_AMOUNT = 100_000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private int discountAmount;

    @Column(nullable = false)
    private int minOrderAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    public Coupon(String name, int discountAmount, int minOrderAmount, Category category,
                  LocalDateTime startDate, LocalDateTime endDate) {
        validateCouponName(name);
        validateDiscountAmount(discountAmount);
        validateMinOrderAmount(minOrderAmount);
        validatePeriod(startDate, endDate);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateCouponName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("이름을 입력해주세요");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("이름은 %d자 이내로 입력해주세요. : %s, %d자", MAX_NAME_LENGTH, name, name.length()));
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 %d 이상 %d 이하여야 합니다. : %d",
                            MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT, discountAmount));
        }

        if (discountAmount % UNIT_DISCOUNT_AMOUNT != 0) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 %d 단위로 설정할 수 있습니다. : %d", UNIT_DISCOUNT_AMOUNT, discountAmount));
        }
    }

    private void validateMinOrderAmount(int price) {
        if (price < MIN_OF_MIN_ORDER_AMOUNT || price > MAX_OF_MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("최소 주문 금액은 %d 이상 %d 이하여야 합니다. : %d",
                            MIN_OF_MIN_ORDER_AMOUNT, MAX_OF_MIN_ORDER_AMOUNT, price));
        }
    }

    private void validatePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    String.format("시작일은 종료일보다 이전이어야 합니다. : %s, %s", startDate, endDate));
        }

        if (endDate.isBefore(LocalDate.now().atStartOfDay())) {
            throw new IllegalArgumentException(
                    String.format("이미 지난 날을 종료일로 설정할 수 없습니다. : %s", endDate)
            );
        }
    }
}
