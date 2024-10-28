package coupon.service;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.entity.MemberCouponEntity;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.support.TransactionSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final TransactionSupporter transactionSupporter;

    @Transactional
    public MemberCoupon issue(Long couponId, Member member) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }

        int issuedCouponCount = memberCouponRepository.countByCouponIdAndMemberId(couponId, member.getId());
        MemberCoupon memberCoupon = MemberCoupon.issue(couponId, member, issuedCouponCount);

        MemberCouponEntity memberCouponEntity = memberCouponRepository.save(MemberCouponEntity.from(memberCoupon));

        return memberCouponEntity.toMemberCoupon();
    }

    @Transactional(readOnly = true)
    public List<MemberCoupon> getCoupons(Member member) {
        List<MemberCouponEntity> memberCoupons = getMemberCoupon(member.getId());

        return memberCoupons.stream()
                .map(MemberCouponEntity::toMemberCoupon)
                .toList();
    }

    private List<MemberCouponEntity> getMemberCoupon(long memberId) {
        List<MemberCouponEntity> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        if (memberCoupons.isEmpty()) {
            return transactionSupporter.executeNewTransaction(() -> memberCouponRepository.findAllByMemberId(memberId));
        }
        return memberCoupons;
    }
}
