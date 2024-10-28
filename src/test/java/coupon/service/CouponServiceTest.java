package coupon.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import coupon.data.Coupon;
import coupon.domain.coupon.CouponMapper;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class CouponServiceTest {

    private static final Coupon entity = new Coupon(
            "potato",
            1000,
            5000,
            "FOOD",
            LocalDateTime.of(2024, 11, 8, 9, 10),
            LocalDateTime.of(2024, 11, 8, 9, 10)
    );
    private static final coupon.domain.coupon.Coupon coupon = CouponMapper.fromEntity(entity);

    @Autowired
    private CouponService couponService;

    @Autowired
    @Qualifier("readerDataSource")
    private DataSource readerDataSource;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate = new JdbcTemplate(readerDataSource);
    }

    @DisplayName("복제가 지연 되는 것을 확인한다.")
    @Test
    void delayedData() {
        Coupon couponEntity = couponService.create(coupon);

        Assertions.assertThatThrownBy(() -> jdbcTemplate.queryForObject(
                        "select * from coupon where id = ?", Coupon.class, couponEntity.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("지연 시간 이후 데이터가 복제된다.")
    @Test
    void delayWaiting() throws InterruptedException {
        Coupon expected = couponService.create(coupon);
        final int WAITING_TIME_IN_NANO = 2000;
        Thread.sleep(WAITING_TIME_IN_NANO);

        Coupon actual = jdbcTemplate.queryForObject("select * from coupon where id = ?",
                (rs, rowNum) -> new Coupon(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("discount_amount"),
                        rs.getInt("minimum_order_amount"),
                        rs.getString("category"),
                        rs.getTimestamp("begin_at").toLocalDateTime(),
                        rs.getTimestamp("end_at").toLocalDateTime()),
                expected.getId());

        Assertions.assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("복제 지연이 일어나면 writer가 데이터를 읽는다.")
    @Test
    void delayResolved() {
        Coupon expected = couponService.create(coupon);

        Coupon actual = couponService.findCoupon(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }
}
