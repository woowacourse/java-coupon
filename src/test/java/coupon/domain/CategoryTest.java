package coupon.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void 카테고리는_패션_가전_가구_식품_중_하나여야_한다() {
        String description = "뷰티";
        assertThatThrownBy(() -> Category.from(description))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("존재하지 않는 카테고리입니다. : " + description);
    }
}
