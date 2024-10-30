package coupon.membercoupon.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.service.MemberCouponService;

@WebMvcTest(MemberCouponController.class)
public class MemberCouponControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberCouponService memberCouponService;

    @DisplayName("해당하는 멤버 ID의 멤버 쿠폰 정보를 가져올 수 있다.")
    @Test
    void testGetMemberCouponsByMemberId() throws Exception {
        Long memberId = 1L;
        Long couponId1 = 1L;
        Long couponId2 = 2L;

        MemberCoupon memberCoupon1 = new MemberCoupon(memberId, couponId1);
        MemberCoupon memberCoupon2 = new MemberCoupon(memberId, couponId2);

        when(memberCouponService.readAllByMemberId(memberId)).thenReturn(List.of(memberCoupon1, memberCoupon2));

        mockMvc.perform(get("/membercoupons/member/{memberId}", memberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberCouponResponses[0].couponId").value(couponId1))
                .andExpect(jsonPath("$.memberCouponResponses[1].couponId").value(couponId2));
    }
}
