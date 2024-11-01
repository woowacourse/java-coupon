package coupon.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.domain.entity.CouponEntity;
import coupon.member.domain.Member;
import coupon.member.domain.MemberCoupon;
import coupon.member.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON = 5;

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public MemberCoupon create(Member member, CouponEntity couponEntity) {
        validateMemberCouponCount(member.getId(), couponEntity.getId());
        MemberCoupon memberCoupon = new MemberCoupon(member, couponEntity);
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateMemberCouponCount(Long memberId, Long couponId) {
        if (getSameCouponSize(memberId, couponId) >= MAX_MEMBER_COUPON) {
            throw new IllegalArgumentException("회원 당 동일한 쿠폰을 사용한 쿠폰을 포함하여 최대 " + MAX_MEMBER_COUPON + "장까지 발급할 수 있다.");
        }
    }

    private int getSameCouponSize(Long memberId, Long couponId) {
        return getCouponIdsByMemberId(memberId)
                .stream()
                .filter(id -> id.equals(couponId))
                .toList()
                .size();
    }

    @Transactional(readOnly = true)
    public List<Long> getCouponIdsByMemberId(Long memberId) {
        return memberCouponRepository.findAllIdByMemberId(memberId);
    }
}
