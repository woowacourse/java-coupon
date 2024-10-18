package coupon.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryTest {

    @Test
    @DisplayName("서비스의 카테고리는 네 종류가 있다.")
    void category() {
        Category[] values = Category.values();

        assertThat(values.length).isEqualTo(4);
    }

    @ParameterizedTest
    @CsvSource(value = {"패션, FASHION", "식품, FOOD"})
    @DisplayName("하나의 카테고리를 선택할 수 있다.")
    void from(String source, Category expected) {
        Category actual = Category.from(source);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("일치하는 카테고리가 없다면 예외가 발생한다.")
    void notFoundCategory() {
        assertThatThrownBy(() -> Category.from("아루"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
