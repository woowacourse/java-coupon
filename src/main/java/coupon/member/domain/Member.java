package coupon.member.domain;

import coupon.coupon.domain.MemberCoupon;
import lombok.Getter;

import java.util.List;

@Getter
public class Member {

    private Long id;
    private String name;
    private List<MemberCoupon> memberCoupons;
}
