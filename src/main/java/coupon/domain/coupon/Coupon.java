package coupon.domain.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PACKAGE)
public class Coupon {
    private Name name;
    private Accounting accounting;
    private Category category;
    private Duration duration;

    Coupon(Name name, Accounting accounting, Category category, Duration duration) {
        this.name = name;
        this.accounting = accounting;
        this.category = category;
        this.duration = duration;
    }

}
