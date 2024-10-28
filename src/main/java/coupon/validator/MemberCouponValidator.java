package coupon.validator;

import coupon.entity.Member;
import coupon.exception.CouponErrorMessage;
import coupon.exception.CouponException;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCouponValidator {

    private static final int MAX_ISSUABLE_COUNT = 5;
    private final MemberCouponRepository memberCouponRepository;


    public void validateIssuedCount(Member member) {
        if (memberCouponRepository.countByMemberId(member.getId()) >= MAX_ISSUABLE_COUNT) {
            throw new CouponException(CouponErrorMessage.EXCEEDED_MAX_COUPON_COUNT);
        }
    }
}
