package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.domain.dto.CouponInfo;
import coupon.domain.dto.MemberCouponCreateRequest;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberCouponServiceTest {

    @Mock
    private MemberCouponRepository mockRepository;

    @InjectMocks
    private MemberCouponService mockService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberCouponService memberCouponService;

    private MemberCouponCreateRequest request;

    @BeforeEach
    void setUp() {
        request = new MemberCouponCreateRequest(1L, 1L);
    }

    @AfterEach
    void tearDown() {
        couponRepository.deleteAll();
        mockRepository.deleteAll();
        memberCouponRepository.deleteAll();
    }

    @Test
    @DisplayName("멤버 쿠폰을 발급할 수 있다.")
    void create() {
        //when
        MemberCoupon result = memberCouponService.create(request);

        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(request.couponId(), result.getCouponId()),
                () -> assertEquals(request.memberId(), result.getMemberId())
        );
    }

    @Test
    @DisplayName("하나의 멤버가 동일한 쿠폰을 6장 이상 발급 받을 수 없다.")
    void create_failWithMoreThan5() {
        //given
        when(mockRepository.countByCouponIdAndMemberId(request.couponId(), request.memberId())).thenReturn(6);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                mockService.create(request)
        );

        //then
        assertEquals("한 사람은 동일한 쿠폰을 5장까지 발급받을 수 있습니다.", exception.getMessage());
    }

    @Transactional
    @Test
    @DisplayName("복제 지연 없이 발급된 쿠폰을 바로 조회할 수 있다.")
    void get() {
        //given
        Coupon coupon = couponRepository.save(new Coupon("coupon", 3000, 100000,
                Category.FASHION, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 1)));
        request = new MemberCouponCreateRequest(coupon.getId(), 1L);
        MemberCoupon memberCoupon = memberCouponService.create(request);

        //when
        List<CouponInfo> result = memberCouponService.get(request.memberId());

        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals(coupon, result.get(0).coupon()),
                () -> assertEquals(memberCoupon, result.get(0).memberCoupon())
        );
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰을 조회할 수 없다.")
    void get_fail() {
        //given
        Long memberId = 1L;

        //when
        List<CouponInfo> result = memberCouponService.get(request.memberId());

        //then
        assertThat(result).hasSize(0);
    }
}

