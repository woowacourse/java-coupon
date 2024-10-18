package coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_DISCOUNT_PERCENT = 3;
    private static final int MAX_DISCOUNT_PERCENT = 20;
    private static final int MIN_ORDER_PRICE = 5_000;
    private static final int MAX_ORDER_PRICE = 100_000;
    private static final int ISSUE_HOUR = 0;
    private static final int ISSUE_MINUTE = 0;
    private static final int ISSUE_SECOND = 0;
    private static final int ISSUE_NANO_SECOND = 0;
    private static final int EXPIRATION_HOUR = 23;
    private static final int EXPIRATION_MINUTE = 59;
    private static final int EXPIRATION_SECOND = 59;
    private static final int EXPIRATION_NANO_SECOND = 999_999;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount_amount")
    private Integer discountAmount;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "minimum_order_price")
    private Integer minimumOrderPrice;

    @Column(name = "issue_date_time")
    private LocalDateTime issueDateTime;

    @Column(name = "expiration_date_time")
    private LocalDateTime expirationDateTime;

    public Coupon(String name, Integer discountAmount, Category category, Integer minimumOrderPrice,
                  LocalDate issueDate, LocalDate expirationDate) {
        validateNameLength(name);
        validateDiscountAmountRange(discountAmount);
        validateDiscountAmountUnit(discountAmount);
        int discountPercent = (discountAmount * 100) / minimumOrderPrice;
        validateDiscountPercent(discountAmount, discountPercent);
        validateMinimumOrderPriceRange(minimumOrderPrice);
        validateIssueDateTime(issueDate, expirationDate);
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountPercent = discountPercent;
        this.category = category;
        this.minimumOrderPrice = minimumOrderPrice;
        this.issueDateTime = issueDate.atTime(ISSUE_HOUR, ISSUE_MINUTE, ISSUE_SECOND, ISSUE_NANO_SECOND);
        this.expirationDateTime = expirationDate.atTime(EXPIRATION_HOUR, EXPIRATION_MINUTE, EXPIRATION_SECOND, EXPIRATION_NANO_SECOND);
    }

    public Coupon(Integer discountAmount, Integer minimumOrderPrice) {
        this("쿠폰", discountAmount, Category.FASHION, minimumOrderPrice, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    private void validateNameLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format("이름의 길이는 최대 %d자 이하여야 합니다.", MAX_NAME_LENGTH));
        }
    }

    private void validateDiscountAmountRange(Integer discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || MAX_DISCOUNT_AMOUNT < discountAmount) {
            throw new IllegalArgumentException(String.format("할인 금액은 %d원 이상, %d원 이하여야 합니다.", MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT));
        }
    }

    private void validateDiscountPercent(Integer discountAmount, int discountPercent) {
        if (discountPercent < MIN_DISCOUNT_PERCENT || MAX_DISCOUNT_PERCENT < discountPercent) {
            System.out.println("discountPercent = " + discountPercent);
            throw new IllegalArgumentException(
                    String.format("할인율은 %d%% 이상 %d%% 이하여야 합니다.", MIN_DISCOUNT_PERCENT, MAX_DISCOUNT_PERCENT));
        }
    }

    private void validateDiscountAmountUnit(Integer discountAmount) {
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액은 %d원 단위로 입력 가능합니다.", DISCOUNT_AMOUNT_UNIT));
        }
    }

    private void validateMinimumOrderPriceRange(Integer minimumOrderPrice) {
        if (minimumOrderPrice < MIN_ORDER_PRICE || MAX_ORDER_PRICE < minimumOrderPrice) {
            throw new IllegalArgumentException(String.format("최소 주문 금액은 %d원 이상, %d원 이하여야 합니다.", MIN_ORDER_PRICE, MAX_ORDER_PRICE));
        }
    }

    private void validateIssueDateTime(LocalDate issueDate, LocalDate expirationDate) {
        if (issueDate.isAfter(expirationDate)) {
            throw new IllegalArgumentException("쿠폰의 발급 기간 시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
