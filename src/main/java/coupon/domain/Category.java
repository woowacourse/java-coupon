package coupon.domain;

import lombok.Getter;

@Getter
public enum Category {

    FASHION("패션"),
    ELECTRONICS("가전"),
    FURNITURE("가구"),
    FOOD("식품"),
    ;

    private final String description;

    Category(String description) {
        this.description = description;
    }
}
