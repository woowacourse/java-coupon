package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class CouponCategoryTest {

    @DisplayName("카테고리를 가져올 수 있다.")
    @EnumSource(CouponCategory.class)
    @ParameterizedTest
    void getCategoryTest(CouponCategory category) {
        // given & when & then
        assertDoesNotThrow(() -> CouponCategory.getCategory(category.name()));
    }

    @DisplayName("카테고리에 등록되지 않은 문자열이면, 예외를 발생한다.")
    @Test
    void getCategoryTest_WhenWrongCategory() {
        // given
        String wrongCategory = "ANIMAL";
        assertThatThrownBy(() -> CouponCategory.getCategory(wrongCategory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("일치하는 카테고리가 없습니다.");
    }
}
