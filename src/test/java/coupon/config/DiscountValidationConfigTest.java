package coupon.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DiscountValidationConfigTest {

    @Autowired
    private DiscountValidationConfig discountValidationConfig;

    @Test
    @DisplayName("yml 파일에서 설정을 불러온다.")
    void load_with_yml_file() {
        assertThat(discountValidationConfig.discountMinPrice()).isEqualTo(BigDecimal.valueOf(1000));
    }
}
