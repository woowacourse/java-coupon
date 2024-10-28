package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.CouponErrorMessage;
import coupon.exception.CouponException;
import java.util.concurrent.Callable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
class FallbackReadServiceTest {

    @Autowired
    private FallbackReadService fallbackReadService;

    @DisplayName("읽기 과정에서 예외가 발생하지 않으면 결과를 반환한다.")
    @Test
    void read() {
        Callable<Object> callable = () -> "result";

        Object result = fallbackReadService.read(callable);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("result");
    }

    @DisplayName("읽기 과정에서 CouponException이 발생하면 그 예외를 그대로 던진다.")
    @Test
    void read_whenCouponExceptionOccurred() {
        Callable<Object> callable = () -> {
            throw new CouponException(CouponErrorMessage.COUPON_NOT_FOUND);
        };

        assertThatThrownBy(() -> fallbackReadService.read(callable))
                .isInstanceOf(CouponException.class)
                .hasMessage(CouponErrorMessage.COUPON_NOT_FOUND.getMessage());
    }

    @DisplayName("읽기 과정에서 CouponException 이외의 예외가 발생하면 FAILED_TO_READ 예외를 던진다.")
    @Test
    void read_whenExceptionOccurred() {
        Callable<Object> callable = () -> {
            throw new RuntimeException();
        };

        assertThatThrownBy(() -> fallbackReadService.read(callable))
                .isInstanceOf(CouponException.class)
                .hasMessage(CouponErrorMessage.FAILED_TO_READ.getMessage());
    }
}
