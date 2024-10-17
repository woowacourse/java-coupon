package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 30;

    private static final long MIN_DISCOUNT_MONEY = 1_000;
    private static final long MAX_DISCOUNT_MONEY = 10_000;
    private static final long DISCOUNT_MONEY_UNIT = 500;

    private static final long MIN_MINIMUM_ORDER_MONEY = 5_000;
    private static final long MAX_MINIMUM_ORDER_MONEY = 100_000;

    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private long discountMoney;
    private long minimumOrderMoney;
    private LocalDate sinceDate;
    private LocalDate untilDate;

    public Coupon(String name, long discountMoney, long minimumOrderMoney, LocalDate sinceDate, LocalDate untilDate) {
        validate(name, discountMoney, minimumOrderMoney, sinceDate, untilDate);

        this.name = name;
        this.discountMoney = discountMoney;
        this.minimumOrderMoney = minimumOrderMoney;
        this.sinceDate = sinceDate;
        this.untilDate = untilDate;
    }

    private void validate(String name, long discountMoney, long minimumOrderMoney,
            LocalDate sinceDate, LocalDate untilDate) {
        validateName(name);
        validateDiscountMoney(discountMoney);
        validateMinimumOrderMoney(minimumOrderMoney);
        validateDiscountRate(discountMoney, minimumOrderMoney);
        validatePeriod(sinceDate, untilDate);
    }

    private void validateName(String name) {
        if (name == null) {
            throw new CouponException("이름은 null일 수 없습니다.");
        }

        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new CouponException(String.format("이름은 %d자 이상 %d자 이하로만 입력 가능합니다.",
                    MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
    }

    private void validateDiscountMoney(long discountMoney) {
        if (discountMoney < MIN_DISCOUNT_MONEY || discountMoney > MAX_DISCOUNT_MONEY) {
            throw new CouponException(String.format("할인 금액은 %d원 이상 %d원 이하로만 입력 가능합니다.",
                    MIN_DISCOUNT_MONEY, MAX_DISCOUNT_MONEY));
        }

        if (discountMoney % DISCOUNT_MONEY_UNIT != 0) {
            throw new CouponException(String.format("할인 금액은 %d원 단위로만 입력 가능합니다.", DISCOUNT_MONEY_UNIT));
        }
    }

    private void validateMinimumOrderMoney(long minimumOrderMoney) {
        if (minimumOrderMoney < MIN_MINIMUM_ORDER_MONEY || minimumOrderMoney > MAX_MINIMUM_ORDER_MONEY) {
            throw new CouponException(String.format("최소 주문 금액은 %d원 이상 %d원 이하로만 입력 가능합니다.",
                    MIN_MINIMUM_ORDER_MONEY, MAX_MINIMUM_ORDER_MONEY));
        }
    }

    private void validateDiscountRate(long discountMoney, long minimumOrderMoney) {
        long discountRate = (discountMoney * 100 / minimumOrderMoney);

        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new CouponException(String.format("할인율은 %d%% 이상 %d%% 이하여야 합니다. input=%d",
                    MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE, discountRate));
        }
    }

    private void validatePeriod(LocalDate sinceDate, LocalDate untilDate) {
        if (sinceDate == null || untilDate == null) {
            throw new CouponException("시작일, 종료일은 null일 수 없습니다.");
        }

        if (sinceDate.isAfter(untilDate)) {
            throw new CouponException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
