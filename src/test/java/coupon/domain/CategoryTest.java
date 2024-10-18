package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CategoryTest {

    @ParameterizedTest
    @CsvSource(value = {"패션,FASHION", "가전,ELECTRONICS", "가구,FURNITURE", "식품,FOOD"})
    void 일치하는_카테고리를_찾는다(String name, Category expected) {
        Category actual = Category.from(name);
        assertThat(actual).isEqualTo(expected);
    }
}
