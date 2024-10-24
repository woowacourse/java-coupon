package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.CouponException;
import coupon.dto.CouponCreateRequest;
import coupon.dto.CouponIssueRequest;
import coupon.dto.MemberCouponResponse;
import coupon.dto.MemberCouponResponses;
import coupon.entity.Category;
import coupon.entity.Coupon;
import coupon.entity.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        couponRepository.deleteAll();
        memberCouponRepository.deleteAll();
        cacheManager.getCache("member_coupon").clear();
        cacheManager.getCache("coupon").clear();
    }

    @DisplayName("쿠폰을 발행한다. 발행한 쿠폰을 조회하는 경우, 쿠폰의 데이터도 함께 조회한다.")
    @Test
    void issue() throws InterruptedException {
        // given
        Coupon coupon = couponService.create(new CouponCreateRequest(
                "name",
                5000,
                50000,
                Category.FOOD,
                LocalDate.of(2024, 10, 11),
                LocalDate.of(2025, 10, 30)
        ));
        long memberId = 1L;
        CouponIssueRequest couponIssueRequest = new CouponIssueRequest(
                coupon.getId(), memberId, LocalDate.of(2024, 10, 5));

        // when
        MemberCoupon issued = memberCouponService.issue(couponIssueRequest);
        Thread.sleep(5000);

        // then
        MemberCouponResponses founds = memberCouponService.findByMemberId(memberId);
        assertThat(founds.memberCouponResponses().size()).isEqualTo(1);
        MemberCouponResponse found = founds.memberCouponResponses().get(0);
        assertAll(
                () -> assertThat(found.used()).isEqualTo(issued.isUsed()),
                () -> assertThat(found.start()).isEqualTo(issued.getStart()),
                () -> assertThat(found.end()).isEqualTo(issued.getEnd()),

                () -> assertThat(found.couponName()).isEqualTo(coupon.getName()),
                () -> assertThat(found.discount()).isEqualTo(coupon.getDiscount()),
                () -> assertThat(found.minimumOrder()).isEqualTo(coupon.getMinimumOrder()),
                () -> assertThat(found.category()).isEqualTo(coupon.getCategory()),
                () -> assertThat(found.couponStart()).isEqualTo(coupon.getStart()),
                () -> assertThat(found.couponEnd()).isEqualTo(coupon.getEnd())
        );
    }

    @DisplayName("동일한 쿠폰을 이미 5장 발급받은 경우, 다시 발급을 시도하면 예외가 발생한다.")
    @Test
    void issue_tooManyDuplication() {
        // given
        Coupon coupon = couponService.create(new CouponCreateRequest(
                "name",
                5000,
                50000,
                Category.FOOD,
                LocalDate.of(2024, 10, 11),
                LocalDate.of(2025, 10, 30)
        ));
        long memberId = 1L;
        CouponIssueRequest couponIssueRequest = new CouponIssueRequest(
                coupon.getId(), memberId, LocalDate.of(2024, 10, 5));
        for (int i = 0; i < 5; i++) {
            memberCouponService.issue(couponIssueRequest);
        }

        // when&then
        assertThatThrownBy(() -> memberCouponService.issue(couponIssueRequest))
                .isInstanceOf(CouponException.class);
    }
}
