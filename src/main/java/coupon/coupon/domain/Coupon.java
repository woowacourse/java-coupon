package coupon.coupon.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import coupon.coupon.CouponException;

@Entity
public class Coupon {

    public static final String CATEGORY_NON_NULL_MESSAGE = "카테고리를 선택해주세요.";
    private static final BigDecimal MINIMUM_DISCOUNT_RATE = BigDecimal.valueOf(3);
    private static final BigDecimal MAXIMUM_DISCOUNT_RATE = BigDecimal.valueOf(20);
    private static final String DISCOUNT_RATE_MESSAGE = String.format(
            "할인율은 %s%% 이상, %s%% 이하이어야 합니다.",
            MINIMUM_DISCOUNT_RATE.toPlainString(),
            MAXIMUM_DISCOUNT_RATE.toPlainString()
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinimumOrderAmount minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Embedded
    private Term term;

    protected Coupon() {
    }

    public Coupon(String name, long discountAmount, long minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        this(new CouponName(name), new DiscountAmount(discountAmount), new MinimumOrderAmount(minimumOrderAmount), category, new Term(startAt, endAt));
    }

    public Coupon(CouponName name, DiscountAmount discountAmount, MinimumOrderAmount minimumOrderAmount, Category category, Term term) {
        validateDiscountRate(discountAmount, minimumOrderAmount);
        validateCategoryNull(category);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.term = term;
    }

    private void validateDiscountRate(DiscountAmount discountAmount, MinimumOrderAmount minimumOrderAmount) {
        BigDecimal discountRate = discountAmount.getDiscountRate(minimumOrderAmount);
        if (discountRate.compareTo(MINIMUM_DISCOUNT_RATE) < 0 || discountRate.compareTo(MAXIMUM_DISCOUNT_RATE) > 0) {
            throw new CouponException(DISCOUNT_RATE_MESSAGE);
        }
    }

    private void validateCategoryNull(Category category) {
        if (Objects.isNull(category)) {
            throw new CouponException(CATEGORY_NON_NULL_MESSAGE);
        }
    }

    public boolean isUnableToIssue(LocalDateTime issuedAt) {
        return term.doesNotContain(issuedAt);
    }

    public Long getId() {
        return id;
    }
}
