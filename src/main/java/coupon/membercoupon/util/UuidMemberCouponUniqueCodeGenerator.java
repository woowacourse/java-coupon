package coupon.membercoupon.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UuidMemberCouponUniqueCodeGenerator implements MemberCouponUniqueCodeGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
