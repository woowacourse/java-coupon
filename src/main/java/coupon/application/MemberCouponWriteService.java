package coupon.application;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_ISSUANCE_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;

    public MemberCoupon create(MemberCoupon memberCoupon) {
        validate(memberCoupon);
        return memberCouponRepository.save(memberCoupon);
    }

    private void validate(MemberCoupon memberCoupon) {
        Member member = memberCoupon.getMember();
        Coupon coupon = memberCoupon.getCoupon();

        Long issuanceCount = memberCouponRepository.countByMember_IdAndCoupon_Id(member.getId(), coupon.getId());

        if (issuanceCount > MAX_ISSUANCE_COUNT) {
            throw new IllegalArgumentException("동일한 쿠폰을 사용한 쿠폰을 포함하여 최대 " + MAX_ISSUANCE_COUNT + "장까지 발급할 수 있습니다.");
        }
    }
}
