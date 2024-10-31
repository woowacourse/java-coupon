package coupon.membercoupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.member.repository.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.dto.MemberCouponRequest;
import coupon.membercoupon.dto.MemberCouponResponse;
import coupon.membercoupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_NUMBER_OF_COUPONS_ISSUED = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public MemberCouponResponse issueCoupon(MemberCouponRequest request) {
        long couponId = request.couponId();
        long memberId = request.memberId();
        Coupon coupon = getCoupon(couponId);

        validateMemberExists(memberId);
        validateIssuedCouponSize(couponId, memberId);
        validateIssuancePeriod(coupon, request.issuanceDate());

        MemberCoupon issuedMemberCoupon = memberCouponRepository.save(request.toEntity(coupon));
        return MemberCouponResponse.from(issuedMemberCoupon);
    }

    private void validateMemberExists(long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("일치하는 멤버를 찾을 수 없습니다.");
        }
    }

    private void validateIssuedCouponSize(long couponId, long memberId) {
        Long size = memberCouponRepository.countByCouponIdAndMemberId(couponId, memberId);

        if (size >= MAX_NUMBER_OF_COUPONS_ISSUED) {
            throw new IllegalArgumentException("한 명의 회원에게 동일한 쿠폰을 최대 %d장까지 발급 가능합니다.".formatted(MAX_NUMBER_OF_COUPONS_ISSUED));
        }
    }

    private void validateIssuancePeriod(Coupon coupon, LocalDate issuanceDate) {
        if (coupon.isNotBetweenIssuancePeriod(issuanceDate)) {
            throw new IllegalArgumentException("쿠폰을 발급 가능한 날짜가 아닙니다.");
        }
    }

    private Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰 아이디와 일치하는 쿠폰을 찾을 수 없습니다. 입력된 id:" + couponId));
    }
}
