package coupon.membercoupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.member.repository.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.dto.MemberCouponItem;
import coupon.membercoupon.repository.MemberCouponRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCouponService {

    private static final int MAX_ISSUE_COUNT = 5;

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponService(CouponRepository couponRepository,
                               MemberRepository memberRepository,
                               MemberCouponRepository memberCouponRepository) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
    }

    @Transactional
    public void create(Long couponId, Long memberId) {
        validateMemberAndCoupon(couponId, memberId);
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId, LocalDateTime.now());
        memberCouponRepository.save(memberCoupon);
    }

    private void validateMemberAndCoupon(Long couponId, Long memberId) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        int issuedCount = memberCouponRepository.countByCouponIdAndMemberId(couponId, memberId);
        if (issuedCount >= MAX_ISSUE_COUNT) {
            throw new IllegalArgumentException("더 이상 발급할 수 없는 쿠폰입니다. 최대 발급 횟수 : " + MAX_ISSUE_COUNT);
        }
    }

    @Transactional
    public List<MemberCouponItem> getAllMemberCouponByMemberId(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        List<MemberCouponItem> memberCouponItems = new ArrayList<>();
        for (MemberCoupon memberCoupon : memberCoupons) {
            Coupon coupon = couponRepository.findById(memberCoupon.getCouponId())
                    .orElseThrow(IllegalArgumentException::new);
            memberCouponItems.add(new MemberCouponItem(memberCoupon, coupon));
        }
        return memberCouponItems;
    }
}
