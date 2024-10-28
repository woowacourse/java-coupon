package coupon.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisConfigPropertiesTest {

    @Autowired
    private RedisConfigProperties sut;

    @Test
    @DisplayName("설정 값 로딩 성공")
    void load() {
        assertAll(
                () -> assertThat(sut.host()).isEqualTo("localhost"),
                () -> assertThat(sut.port()).isEqualTo(36379),
                () -> assertThat(sut.ttl()).isEqualTo(Duration.ofDays(7))
        );
    }
}
