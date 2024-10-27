package coupon.domain.coupon;

import static org.junit.jupiter.api.Assertions.*;

import coupon.exception.CategoryNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CategoryTest {

    @DisplayName("존재하지 않는 카테고리라면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"nothing", "one", "two", "three"})
    void from(String category) {
        Assertions.assertThatThrownBy(() -> Category.from(category))
                .isInstanceOf(CategoryNotFoundException.class);
    }
}
