package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.domain.dto.MemberCouponCreateRequest;
import coupon.repository.MemberCouponRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class MemberCouponServiceTest {

    @Mock
    private MemberCouponRepository memberCouponRepository;

    @InjectMocks
    private MemberCouponService memberCouponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        memberCouponRepository.deleteAll();
    }

    @Test
    @DisplayName("멤버 쿠폰을 발급할 수 있다.")
    void create() {
        MemberCouponCreateRequest request = new MemberCouponCreateRequest(1L, 1L);
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, LocalDateTime.now());

        when(memberCouponRepository.countByCouponIdAndMemberId(request.couponId(), request.memberId())).thenReturn(5);
        when(memberCouponRepository.save(any(MemberCoupon.class))).thenReturn(memberCoupon);

        MemberCoupon result = memberCouponService.create(request);

        assertNotNull(result);
        assertEquals(memberCoupon.getId(), result.getId());
    }

    @Test
    @DisplayName("하나의 멤버가 5장 이상의 동일한 쿠폰을 발급 받을 수 없다.")
    void create_failWithMoreThan5() {
        MemberCouponCreateRequest request = new MemberCouponCreateRequest(1L, 1L);

        when(memberCouponRepository.countByCouponIdAndMemberId(request.couponId(), request.memberId())).thenReturn(6);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberCouponService.create(request)
        );

        assertEquals("한 사람은 동일한 쿠폰을 5장까지 발급받을 수 있습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("쿠폰을 조회할 수 있다.")
    void get() {
        Long memberCouponId = 1L;
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, LocalDateTime.now());

        when(memberCouponRepository.findById(memberCouponId)).thenReturn(Optional.of(memberCoupon));

        MemberCoupon result = memberCouponService.get(memberCouponId);

        assertNotNull(result);
        assertEquals(memberCouponId, result.getMemberId());
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰을 조회할 수 없다.")
    void get_fail() {
        Long memberCouponId = 1L;

        when(memberCouponRepository.findById(memberCouponId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberCouponService.get(memberCouponId)
        );

        assertEquals("해당하는 쿠폰이 존재하지 않습니다.", exception.getMessage());
    }
}

