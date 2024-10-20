package coupon.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import coupon.data.Coupon;
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
        Coupon coupon = new Coupon("potato");
        couponService.create(coupon);

        Assertions.assertThatThrownBy(() -> jdbcTemplate.queryForObject(
                        "select * from coupon where id = ?", Coupon.class, coupon.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("지연된 시간만큼 이후 데이터가 복제된다.")
    @Test
    void delayWaiting() throws InterruptedException {
        Coupon expected = couponService.create(new Coupon("potato"));
        final int WAITING_TIME_IN_NANO = 2000;
        Thread.sleep(WAITING_TIME_IN_NANO);

        Coupon actual = jdbcTemplate.queryForObject("select * from coupon where id = ?",
                (rs, rn) -> new Coupon(rs.getLong(1), rs.getString(2))
                , expected.getId());

        Assertions.assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("복제 지연이 일어나면 writer가 데이터를 읽는다.")
    @Test
    void delayResolved() {
        Coupon expected = new Coupon("potato");
        couponService.create(expected);

        Coupon actual = couponService.getCoupon(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }
}
