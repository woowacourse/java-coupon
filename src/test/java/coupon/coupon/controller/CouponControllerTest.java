package coupon.coupon.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponService;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCoupons;
import coupon.membercoupon.service.MemberCouponService;

@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CouponService couponService;
    @MockBean
    private MemberCouponService memberCouponService;

    @DisplayName("해당하는 멤버 ID의 쿠폰 정보를 가져올 수 있다.")
    @Test
    void testGetCouponsByMember() throws Exception {
        Long memberId = 1L;
        Long couponId1 = 1L;
        Long couponId2 = 2L;

        MemberCoupon memberCoupon1 = new MemberCoupon(memberId, couponId1);
        MemberCoupon memberCoupon2 = new MemberCoupon(memberId, couponId2);

        Coupon coupon1 = new Coupon(couponId1, "coupon_name_1", 1000L, 10000L, "FOOD", LocalDateTime.now(), LocalDateTime.now());
        Coupon coupon2 = new Coupon(couponId2, "coupon_name_2", 1000L, 10000L, "FOOD", LocalDateTime.now(), LocalDateTime.now());

        when(memberCouponService.readAllByMemberId(memberId)).thenReturn(List.of(memberCoupon1, memberCoupon2));
        when(couponService.readAllByIdsFromReaderWithCache(List.of(couponId1, couponId2))).thenReturn(List.of(coupon1, coupon2));

        mockMvc.perform(get("/coupons/member/{memberId}", memberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponResponses[0].id").value(couponId1))
                .andExpect(jsonPath("$.couponResponses[0].name").value("coupon_name_1"))
                .andExpect(jsonPath("$.couponResponses[1].id").value(couponId2))
                .andExpect(jsonPath("$.couponResponses[1].name").value("coupon_name_2"));
    }
}
