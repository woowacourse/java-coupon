package coupon.application.membercoupon;

import coupon.application.coupon.exception.CouponNotFoundException;
import coupon.application.membercoupon.exception.TooManyMemberCouponException;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.repository.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.exception.MemberNotFoundException;
import coupon.domain.member.repository.MemberRepository;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.repository.MemberCouponRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCouponWriteService {

    public static final int MAX_COUPON_COUNT_PER_MEMBER = 5;
    private final CouponRepository couponRepository;

    private final MemberRepository memberRepository;

    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponCountResponse addCouponToMember(Long couponId, Long memberId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("없는 쿠폰입니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("없는 회원입니다."));

        validateMemberCouponCount(couponId, memberId);

        MemberCouponResponse memberCouponResponse = addCouponToMember(coupon, member);

        return new MemberCouponCountResponse(memberCouponResponse, 1L);
    }

    private void validateMemberCouponCount(Long couponId, Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberIdAndCouponId(memberId, couponId);
        if (memberCoupons.size() >= MAX_COUPON_COUNT_PER_MEMBER) {
            String exceptionMessage = "한 회원에게 같은 쿠폰은 최대 %d장 발급할 수 있습니다.".formatted(MAX_COUPON_COUNT_PER_MEMBER);
            throw new TooManyMemberCouponException(exceptionMessage);
        }
    }

    private MemberCouponResponse addCouponToMember(Coupon coupon, Member member) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, false, startDate, endDate);
        MemberCoupon saved = memberCouponRepository.save(memberCoupon);
        return MemberCouponResponse.from(saved);
    }
}
