package coupon.domain;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Category {
    FASHION("패션"),
    ELECTRONICS("가전"),
    FURNITURE("가구"),
    FOOD("식품");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public static Category from(String description) {
        return Arrays.stream(values())
            .filter(category -> category.getDescription().equals(description))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다. : " + description));
    }
}
