package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.entity.Coupon;
import coupon.entity.CouponCategory;
import coupon.exception.CouponNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

public class CouponServiceTest extends ServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CouponServiceTest.class);

    @Autowired
    private CouponService couponService;

    @Test
    void 쿠폰을_생성할_수_있다() {
        // given
        Coupon coupon = new Coupon(
                "점심 반값 쿠폰",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDate.of(2000, 4, 7),
                LocalDate.of(2000, 4, 12)
        );

        // when & then
        assertThat(couponService.create(coupon)).isEqualTo(1L);
    }

    @Test
    void 생성후_시간이_흐르고서_쿠폰을_조회할_수_있다() throws InterruptedException {
        // given
        Thread.sleep(1000); // 테이블 초기화가 read db 에도 전파될 때까지 대기
        LocalDateTime stamp = LocalDateTime.now();
        String rightName = "조회용" + stamp;
        Coupon coupon = new Coupon(
                rightName,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDate.of(2000, 4, 7),
                LocalDate.of(2000, 4, 12)
        );
        couponService.create(coupon);

        // when
        Thread.sleep(5000); // 복제지연 대기
        Coupon findCoupon = couponService.getCoupon(coupon.getId());

        // then
        assertThat(findCoupon.getName().getName()).isEqualTo(rightName);
    }

    @Test
    void 생성하자마자_쿠폰을_조회할_때_복제_지연이_발생한다() {
        // given
        LocalDateTime stamp = LocalDateTime.now();
        String rightName = "복제지연" + stamp;
        Coupon coupon = new Coupon(
                rightName,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDate.of(2000, 4, 7),
                LocalDate.of(2000, 4, 12)
        );
        couponService.create(coupon);

        // when && then
        /**
         * read db는 초기화 바로 안되서 이전 생성 결과 남아있을 수 있음
         * 이전 생성 결과에 영향 받지 않기 위해 조회 혹시 되는 경우엔 stamp 값으로 이름 확인함
         * */
        try {
            Coupon savedCoupon = couponService.getCoupon(coupon.getId());
            String wrongName = savedCoupon.getName().getName();
            assertThat(wrongName).isNotEqualTo(rightName);
            log.info("EntityManager가 인지한 id 값: " + savedCoupon.getId());
            log.info("복제지연 테스트 성공: 전에 생성한 결과 readDB엔 남아있어서 조회는 되지만, 방금 저장한 값이 아님");
            log.info("readDB에 남아있어 잘못 조회된 값: " + wrongName + " 방금 저장한 값: " + rightName);
        } catch (InvalidDataAccessResourceUsageException e) {
            log.info("복제지연 테스트 성공: 최초 실행이라 테이블이 아예 없는 상태임");
        } catch (CouponNotFoundException e) {
            log.info("복제지연 테스트 성공: 테이블 초기화 성공한 상태라 테이블에 값이 아예 없는 상태임");
        }
    }
}
