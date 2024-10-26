package coupon.fixture;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import java.time.LocalDate;

public enum CouponFixture {

    PRAM_COUPON("pram", 1_000, 30_000, Category.FOOD, LocalDate.now(), LocalDate.now()),
    DAON_COUPON("daon", 1_500, 45_000, Category.APPLICATION, LocalDate.now(), LocalDate.now()),
    BLACKJACK_COUPON("blackjack", 2_000, 60_000, Category.APPLICATION, LocalDate.now(), LocalDate.now()),
    LOTTO_COUPON("lotto", 2_000, 60_000, Category.FASHION, LocalDate.now(), LocalDate.now()),
    CHESS_COUPON("chess", 2_500, 75_000, Category.FURNITURE, LocalDate.now(), LocalDate.now()),
    BASEBALL_COUPON("baseball", 3_000, 90_000, Category.FASHION, LocalDate.now(), LocalDate.now());

    private final String name;
    private final int discountAmount;
    private final int minimumAmount;
    private final Category category;
    private final LocalDate startDate;
    private final LocalDate endDate;

    CouponFixture(
            String name,
            int discountAmount,
            int minimumAmount,
            Category category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumAmount = minimumAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Coupon coupon() {
        return new Coupon(name, discountAmount, minimumAmount, category, startDate, endDate);
    }
}
