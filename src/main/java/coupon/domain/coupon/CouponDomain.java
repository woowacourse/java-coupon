package coupon.domain.coupon;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponDomain {

    private final CouponName couponName;

    private final PriceInformation priceInformation;

    private final Category category;

    private final IssuePeriod issuePeriod;

    public CouponDomain(String couponName,
                        int discountPrice,
                        int minimumPrice,
                        Category category,
                        LocalDate issueDate,
                        LocalDate endDate
    ) {
        this.couponName = new CouponName(couponName);
        this.priceInformation = new PriceInformation(discountPrice, minimumPrice);
        this.category = category;
        this.issuePeriod = new IssuePeriod(issueDate, endDate);
    }

    public String getCouponName() {
        return couponName.getName();
    }

    public int getDiscountPrice() {
        return priceInformation.getDiscountPrice();
    }

    public int getMinimumPrice() {
        return priceInformation.getMinimumPrice();
    }

    public LocalDate getStartDate() {
        return issuePeriod.getStartDate();
    }

    public LocalDate getEndDate() {
        return issuePeriod.getEndDate();
    }
}
