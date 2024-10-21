package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


public class Coupon {

    private final Name name;
    private final DiscountMoney discountMoney;
    private final DiscountRate discountRate;
    private final OrderPrice orderPrice;
    private final Category category;
    private final IssuedPeriod issuedPeriod;

    public Coupon(Name name, DiscountMoney discountMoney, DiscountRate discountRate, OrderPrice orderPrice,
                  Category category, IssuedPeriod issuedPeriod) {
        this.name = Objects.requireNonNull(name);
        this.discountMoney = Objects.requireNonNull(discountMoney);
        this.discountRate = Objects.requireNonNull(discountRate);
        this.orderPrice = Objects.requireNonNull(orderPrice);
        this.category = Objects.requireNonNull(category);
        this.issuedPeriod = Objects.requireNonNull(issuedPeriod);
    }

    public Coupon(Long discountMoney, Long orderPrice) {
        this(
                new Name(UUID.randomUUID().toString().substring(0, 10)),
                new DiscountMoney(discountMoney),
                new DiscountRate(discountMoney, orderPrice),
                new OrderPrice(orderPrice),
                Category.FASHIONS,
                new IssuedPeriod(LocalDate.now())
        );
    }

    public String getName() {
        return name.getName();
    }

    public Long getDiscountMoney() {
        return discountMoney.getDiscountAmount();
    }

    public Long getDiscountRate() {
        return discountRate.getDiscountRate();
    }

    public Long getOrderPrice() {
        return orderPrice.getPrice();
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getStart() {
        return issuedPeriod.getStartDateTime();
    }


    public LocalDateTime getEnd() {
        return issuedPeriod.getEndDateTime();
    }
}
