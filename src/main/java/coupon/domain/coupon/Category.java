package coupon.domain.coupon;

import coupon.exception.CategoryNotFoundException;
import java.util.Arrays;
import lombok.Getter;
import lombok.ToString;

enum Category {
    FASHION, ELECTRONIC, FURNITURE, FOOD;

    public static Category from(String category) {
        return Arrays.stream(values())
                .filter(name -> name.name().equals(category.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new CategoryNotFoundException(category));
    }
}
