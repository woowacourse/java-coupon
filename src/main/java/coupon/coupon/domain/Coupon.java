package coupon.coupon.domain;

import coupon.advice.DomainException;
import coupon.coupon.exception.CouponIssueLimitExceededException;
import coupon.coupon.exception.CouponIssueTimeException;
import coupon.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    private static final int NAME_LENGTH_LIMIT = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_UNIT = 500;
    private static final int MIN_ORDER_PRICE = 5000;
    private static final int MAX_ORDER_PRICE = 100000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "discount_amount")
    private int discountAmount;

    @Column(name = "minimum_order_price")
    private int minimumOrderPrice;

    @Column(name = "category", columnDefinition = "VARCHAR(30)")
    @Enumerated(value = EnumType.STRING)
    private CouponCategory couponCategory;

    @Column(name = "issue_started_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueStartedAt;

    @Column(name = "issue_ended_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueEndedAt;

    @Column(name = "issue_count")
    private Long issueCount;

    @Column(name = "issue_limit")
    private Long issueLimit;

    public Coupon(
            Long id, String name, int discountAmount, int minimumOrderPrice, CouponCategory couponCategory,
            LocalDateTime issueStartedAt, LocalDateTime issueEndedAt, long issueLimit) {

        validate(name, discountAmount, minimumOrderPrice, issueStartedAt, issueEndedAt);

        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderPrice = minimumOrderPrice;
        this.couponCategory = couponCategory;
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
        this.issueCount = 0L;
        this.issueLimit = issueLimit;
    }

    public Coupon(
            String name, int discountAmount, int minimumOrderPrice, CouponCategory couponCategory,
            LocalDateTime issueStartedAt, LocalDateTime issueEndedAt, long issueLimit) {
        this(null, name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt, issueLimit);
    }

    private void validate(
            String name, int discountAmount, int minimumOrderPrice, LocalDateTime issueStartedAt,
            LocalDateTime issueEndedAt) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderPrice(minimumOrderPrice);
        validateDiscountRate(discountAmount, minimumOrderPrice);
        validateIssuePeriod(issueStartedAt, issueEndedAt);
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new DomainException("쿠폰 이름은 반드시 존재해야 합니다.");
        }
        if (name.length() > 30) {
            throw new DomainException(String.format("쿠폰 이름은 최대 %d자 이하여야 합니다.", NAME_LENGTH_LIMIT));
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new DomainException(
                    String.format("할인 금액은 %d원 이상, %d원 이하여야 합니다.", MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT));
        }
        if (discountAmount % DISCOUNT_UNIT != 0) {
            throw new DomainException(String.format("할인 금액은 %d원 단위로 설정해야 합니다.", DISCOUNT_UNIT));
        }
    }

    private void validateMinimumOrderPrice(int minimumOrderPrice) {
        if (minimumOrderPrice < MIN_ORDER_PRICE || minimumOrderPrice > MAX_ORDER_PRICE) {
            throw new DomainException(
                    String.format("최소 주문 금액은 %d원 이상, %d원 이하여야 합니다.", MIN_ORDER_PRICE, MAX_ORDER_PRICE));
        }
    }

    private void validateDiscountRate(int discountAmount, int minimumOrderPrice) {
        double discountRate = getDiscountRate(discountAmount, minimumOrderPrice);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new DomainException(
                    String.format("할인율은 %d%% 이상, %d%% 이하여야 합니다.", MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE));
        }
    }

    private double getDiscountRate(int discountAmount, double minimumOrderPrice) {
        return (discountAmount / minimumOrderPrice) * 100;
    }

    private void validateIssuePeriod(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        if (issueStartedAt == null || issueEndedAt == null) {
            throw new DomainException("발급 시작일과 종료일은 반드시 존재해야 합니다.");
        }
        if (!issueStartedAt.isBefore(issueEndedAt)) {
            throw new DomainException("발급 시작일은 종료일보다 이전이어야 합니다.");
        }
    }

    public void issue() {
        if (this.issueStartedAt.isAfter(LocalDateTime.now()) || this.issueEndedAt.isBefore(LocalDateTime.now())) {
            throw new CouponIssueTimeException();
        }
        if (this.issueLimit <= this.issueCount) {
            throw new CouponIssueLimitExceededException();
        }
        this.issueCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return Objects.equals(getId(), coupon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    //TODO: toString 추가
}
