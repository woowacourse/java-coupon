package coupon.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(DiscountValidationConfig.class)
class DiscountValidationConfigTest {

    @Autowired
    private DiscountValidationConfig discountValidationConfig;

    @Test
    @DisplayName("yml 파일에서 설정을 불러온다.")
    void some() {
        assertThat(discountValidationConfig.discountMinPrice()).isEqualTo(1000);
    }
}
