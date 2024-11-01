package coupon.membercoupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponReader;
import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.dto.MemberCouponRequest;
import coupon.membercoupon.dto.MemberCouponResponse;
import coupon.membercoupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_NUMBER_OF_COUPONS_ISSUED = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponReader couponReader;

    @Transactional
    public MemberCouponResponse issueCoupon(MemberCouponRequest request) {
        long couponId = request.couponId();
        long memberId = request.memberId();
        Coupon coupon = getCoupon(couponId);
        Member member = getMember(memberId);

        validateIssuedCouponSize(couponId, memberId);
        validateIssuancePeriod(coupon, request.issuanceDate());

        MemberCoupon issuedMemberCoupon = memberCouponRepository.save(request.toEntity());
        return MemberCouponResponse.of(issuedMemberCoupon, coupon, member);
    }

    private void validateIssuedCouponSize(long couponId, long memberId) {
        long size = memberCouponRepository.countByCouponIdAndMemberId(couponId, memberId);

        if (size >= MAX_NUMBER_OF_COUPONS_ISSUED) {
            throw new IllegalArgumentException("한 명의 회원에게 동일한 쿠폰을 최대 %d장까지 발급 가능합니다.".formatted(MAX_NUMBER_OF_COUPONS_ISSUED));
        }
    }

    private void validateIssuancePeriod(Coupon coupon, LocalDate issuanceDate) {
        if (coupon.isNotBetweenIssuancePeriod(issuanceDate)) {
            throw new IllegalArgumentException("쿠폰을 발급 가능한 날짜가 아닙니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> findAllMemberCoupon(long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::toMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse toMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = getCoupon(memberCoupon.getCouponId());
        Member member = getMember(memberCoupon.getMemberId());

        return MemberCouponResponse.of(memberCoupon, coupon, member);
    }

    private Coupon getCoupon(long couponId) {
        return couponReader.getCoupon(couponId);
    }

    private Member getMember(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 멤버를 찾을 수 없습니다."));
    }
}
