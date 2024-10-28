package coupon.membercoupon.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponName;
import coupon.coupon.service.port.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.service.port.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.service.port.MemberCouponRepository;

@Transactional(readOnly = true)
@Service
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public MemberCouponService(
            final MemberCouponRepository memberCouponRepository,
            final MemberRepository memberRepository,
            final CouponRepository couponRepository
    ) {
        this.couponRepository = couponRepository;
        this.memberCouponRepository = memberCouponRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void issueCoupon(final Long memberId, final String couponNameValue) {
        final Member member = getMember(memberId);
        final Coupon coupon = getCoupon(couponNameValue);
        checkIssuedCouponCount(member, coupon);

        final MemberCoupon issuedMemberCoupon = MemberCoupon.create(member, coupon, false);
        memberCouponRepository.save(issuedMemberCoupon);
    }

    private Member getMember(final Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("회원 id로 null을 입력할 수 없습니다.");
        }
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 회원 정보가 존재하지 않습니다. - " + memberId));
    }

    private Coupon getCoupon(final String couponNameValue) {
        final CouponName couponName = new CouponName(couponNameValue);
        return couponRepository.findByName(couponName)
                .orElseThrow(() -> new NoSuchElementException("해당 이름의 쿠폰 정보가 존재하지 않습니다. - " + couponNameValue));
    }

    private void checkIssuedCouponCount(final Member member, final Coupon coupon) {
        final int issuedCouponCount = memberCouponRepository.countByMemberAndCoupon(member, coupon);
        if (MemberCoupon.isCouponLimitReached(issuedCouponCount)) {
            throw new IllegalStateException("이미 쿠폰 발급 가능 횟수에 도달하였습니다.");
        }
    }

    public List<MemberCoupon> getMemberCoupons(final Long memberId) {
        final Member member = getMember(memberId);
        return memberCouponRepository.findAllByMember(member);
    }
}
