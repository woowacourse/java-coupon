package coupon.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
public class Coupon {
    private static final int NAME_MAX_LENGTH = 30;
    private static final Long MIN_DISCOUNT_AMOUNT = 1_000L;
    private static final Long MAX_DISCOUNT_AMOUNT = 10_000L;
    private static final Long DISCOUNT_AMOUNT_UNIT = 500L;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final Long MIN_MINIMUM_AMOUNT = 5_000L;
    private static final Long MAX_MINIMUM_AMOUNT = 100_000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "minimum_amount")
    private Long minimumAmount;

    @Column(nullable = false, name = "discount_amount")
    private Long discountAmount;

    @Column(nullable = false, name = "start_issue_date")
    private LocalDate startIssueDate;

    @Column(nullable = false, name = "end_issue_date")
    private LocalDate endIssueDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    protected Coupon() {
    }

    public Coupon(
            String name,
            Long minimumAmount,
            Long discountAmount,
            LocalDate startIssueDate,
            LocalDate endIssueDate,
            Category category) {
        validate(name, minimumAmount, discountAmount, startIssueDate, endIssueDate, category);
        this.name = name;
        this.minimumAmount = minimumAmount;
        this.discountAmount = discountAmount;
        this.startIssueDate = startIssueDate;
        this.endIssueDate = endIssueDate;
        this.category = category;
    }

    private void validate(
            String name,
            Long minimumAmount,
            Long discountAmount,
            LocalDate startIssueDate,
            LocalDate endIssueDate,
            Category category) {
        validateName(name);
        validateMinimumAmount(minimumAmount);
        validateDiscountAmount(discountAmount);
        validateDiscountAmountUnit(discountAmount);
        validateDiscountRate(discountAmount, minimumAmount);
        validateIssueDate(startIssueDate, endIssueDate);
        validateCategory(category);
    }

    private void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("쿠폰 이름은 필수입니다.");
        }

        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("쿠폰 이름은 %d자 이하여야 합니다.", NAME_MAX_LENGTH));
        }
    }

    private void validateMinimumAmount(Long minimumAmount) {
        if (minimumAmount == null) {
            throw new IllegalArgumentException("최소 주문 금액은 필수입니다.");
        }

        if (minimumAmount < MIN_MINIMUM_AMOUNT || minimumAmount > MAX_MINIMUM_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("최소 주문 금액은 %d원 이상 %d원 이하여야 합니다.", MIN_MINIMUM_AMOUNT, MAX_MINIMUM_AMOUNT));
        }
    }

    private void validateDiscountAmount(Long discountAmount) {
        if (discountAmount == null) {
            throw new IllegalArgumentException("할인 금액은 필수입니다.");
        }

        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 %d원 이상 %d원 이하여야 합니다.", MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT));
        }
    }

    private void validateDiscountAmountUnit(Long discountAmount) {
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액은 %d원 단위로 입력해야 합니다.", DISCOUNT_AMOUNT_UNIT));
        }
    }

    private void validateDiscountRate(Long discountAmount, Long minimumAmount) {
        int discountRate = new BigDecimal(discountAmount)
                .multiply(new BigDecimal(100))
                .divide(new BigDecimal(minimumAmount), 0, RoundingMode.DOWN)
                .intValue();

        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(
                    String.format("할인율은 %s 이상 %s 이하여야 합니다.", MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE));
        }
    }

    private void validateIssueDate(LocalDate startIssueDate, LocalDate endIssueDate) {
        if (startIssueDate == null || endIssueDate == null) {
            throw new IllegalArgumentException("발급 기간은 필수입니다.");
        }

        if (startIssueDate.isAfter(endIssueDate)) {
            throw new IllegalArgumentException("발급 시작일은 종료일보다 빠를 수 없습니다.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("카테고리는 필수입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getMinimumAmount() {
        return minimumAmount;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public LocalDate getStartIssueDate() {
        return startIssueDate;
    }

    public LocalDate getEndIssueDate() {
        return endIssueDate;
    }

    public Category getCategory() {
        return category;
    }
}
