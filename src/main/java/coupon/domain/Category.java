package coupon.domain;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
    FASHION("패션"),
    ELECTRONICS("가전"),
    FURNITURE("가구"),
    FOOD("식품");

    private final String displayName;

    public static Category from(String name) {
        return Arrays.stream(values()).filter(value -> value.displayName.equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(name + "과(와) 일치하는 카테고리가 없습니다."));
    }
}
