package coupon.coupon.domain;

import java.util.Arrays;

public enum Category {

    FASHION("패션"),
    HOME_APPLIANCES("가전"),
    FURNITURE("가구"),
    FOOD("식품"),
    ;

    private final String category;

    Category(String category) {
        this.category = category;
    }

    public static Category from(String category) {
        return Arrays.stream(values())
                .filter(c -> c.isCategoryMatch(category))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 카테고리가 없습니다. 입력된 카테고리 : %s".formatted(category)));
    }

    private boolean isCategoryMatch(String category) {
        return this.category.equals(category);
    }

    public String getValue() {
        return category;
    }
}
