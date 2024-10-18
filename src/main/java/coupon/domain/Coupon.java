package coupon.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Coupon {

    private Long id;
    private String name;
    private int discountAmount;
    private int minimumOrderAmount;
    private DiscountRate discountRate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Category category;

    public Coupon(String name, int discountAmount, int minimumOrderAmount, LocalDateTime startDate, LocalDateTime endDate, Category category) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDates(startDate, endDate);
        validateCategory(category);

        this.discountRate = new DiscountRate(discountAmount, minimumOrderAmount);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("쿠폰 이름은 반드시 존재해야 합니다.");
        }
        if (name.length() > 30) {
            throw new IllegalArgumentException("쿠폰 이름은 최대 30자 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < 1000) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상이어야 합니다.");
        }
        if (discountAmount > 10000) {
            throw new IllegalArgumentException("할인 금액은 10,000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위여야 합니다.");
        }
    }

    private void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (minimumOrderAmount < 5000) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상이어야 합니다.");
        }
        if (minimumOrderAmount > 100000) {
            throw new IllegalArgumentException("최소 주문 금액은 100,000원 이하여야 합니다.");
        }
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("카테고리는 패션, 가전, 가구, 식품 중 하나여야 합니다.");
        }
    }

    public int getDiscountRate() {
        return discountRate.intValue();
    }
}
