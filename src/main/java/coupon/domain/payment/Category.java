package coupon.domain.payment;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Category {
    FASHION,
    APPLIANCE,
    FURNITURE,
    FOOD;

    private static final Map<String, Category> CONVERTER = Arrays.stream(values())
            .collect(Collectors.toMap(Category::name, Function.identity()));

    public static Category from(final String text) {
        return CONVERTER.get(text);
    }
}
