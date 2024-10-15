package coupon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Coupon {

    private static final int MAX_LENGTH = 30;
    private static final int MIN_DISCOUNT = 1_000;
    private static final int MAX_DISCOUNT = 10_000;
    private static final int DISCOUNT_UNIT = 500;
    private static final int MIN_MIN_AMOUNT = 5_000;
    private static final int MAX_MIN_AMOUNT = 100_000;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;
    private int discount;
    private int minAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Category category;

    public Coupon() {
    }

    public Coupon(String name, int discount, int minAmount, LocalDate startDate, LocalDate endDate, Category category) {
        this(null, name, discount, minAmount, startDate, endDate, category);
    }

    public Coupon(
            Long id, String name, int discount, int minAmount, LocalDate startDate, LocalDate endDate, Category category
    ) {
        validateName(name);
        validateDiscount(discount);
        validateMinAmount(minAmount);
        validatePeriod(startDate, endDate);
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.minAmount = minAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    private void validateName(String name) {
        if (name.isBlank() || name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("올바르지 않은 쿠폰 이름입니다.");
        }
    }

    private void validateDiscount(int discount) {
        if (discount < MIN_DISCOUNT || discount > MAX_DISCOUNT) {
            throw new IllegalArgumentException("올바르지 않은 할인 금액입니다");
        }
        if (discount % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException("쿠폰 할인 금액은 500원 단위로 설정되어야 합니다.");
        }
    }

    private void validateMinAmount(int minAmount) {
        if (minAmount < MIN_MIN_AMOUNT || minAmount > MAX_MIN_AMOUNT) {
            throw new IllegalArgumentException("올바르지 않은 최소 주문 금액입니다.");
        }
    }

    private void validatePeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
