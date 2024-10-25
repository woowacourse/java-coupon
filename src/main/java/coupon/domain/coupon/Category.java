package coupon.domain.coupon;

import lombok.Getter;

@Getter
public enum Category {
    FASHION("패션"),
    FURNITURE("가구"),
    HOME_APPLIANCE("가전"),
    FOOD("식품"),
    ;

    private final String typeName;

    Category(String typeName) {
        this.typeName = typeName;
    }
}
