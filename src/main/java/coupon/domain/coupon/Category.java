package coupon.domain.coupon;

import coupon.exception.CategoryNotFoundException;
import java.util.Arrays;

enum Category {
    FASHION, ELECTRONIC, FURNITURE, FOOD;

    public static Category from(String category) {
        return Arrays.stream(values())
                .filter(name -> name.name().equals(category))
                .findAny()
                .orElseThrow(() -> new CategoryNotFoundException(category));
    }
}
